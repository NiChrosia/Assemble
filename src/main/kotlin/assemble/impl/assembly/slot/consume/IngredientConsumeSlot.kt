package assemble.impl.assembly.slot.consume

import assemble.api.assembly.slot.ConsumeSlot
import assemble.impl.assembly.adapter.item.ItemAdapter
import assemble.impl.assembly.resource.ItemIngredient
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mojang.serialization.JsonOps
import net.minecraft.item.Item
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtOps
import net.minecraft.network.PacketByteBuf
import net.minecraft.tag.ServerTagManagerHolder
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

open class IngredientConsumeSlot(override val resource: ItemIngredient, override val quantity: Int, val slot: Int) : ConsumeSlot<ItemIngredient, ItemAdapter>() {
    override fun enoughIngredients(inventory: ItemAdapter): Boolean {
        val item = inventory.getItem(slot)
        val nbt = inventory.getNbt(slot)
        val count = inventory.getCount(slot)

        val validType = resource.matches(item)
        val correctNbt = resource.matches(nbt)
        val enoughItems = count >= quantity

        return validType && correctNbt && enoughItems
    }

    override fun consume(inventory: ItemAdapter) {
        val count = inventory.getCount(slot)
        inventory.setCount(slot, count - quantity)
    }

    class Type(val slot: Int) : ConsumeSlot.Type<ItemIngredient, ItemAdapter, IngredientConsumeSlot>() {
        override fun read(json: JsonElement): IngredientConsumeSlot {
            return when(json) {
                is JsonArray -> {
                    val matching = getMatching(json)

                    val resource = ItemIngredient(matching)

                    IngredientConsumeSlot(resource, 1, slot)
                }

                is JsonObject -> {
                    val matching = json["matching"]?.asJsonArray?.let(::getMatching) ?: emptyList()

                    val nbt = JsonOps.INSTANCE.convertTo(NbtOps.INSTANCE, json["data"])
                    val compound = (nbt as? NbtCompound) ?: NbtCompound()

                    val quantity = json["count"]?.asInt ?: 1
                    val resource = ItemIngredient(matching, compound)

                    IngredientConsumeSlot(resource, quantity, slot)
                }

                else -> throw unrecognizedFormat()
            }
        }

        override fun unpack(buffer: PacketByteBuf): IngredientConsumeSlot {
            val keysSize = buffer.readInt()
            val keys = mutableListOf<Identifier>()

            for (i in 1..keysSize) {
                keys.add(buffer.readIdentifier())
            }

            val items = keys.map(Registry.ITEM::get)
            val nbt = buffer.readUnlimitedNbt() ?: NbtCompound()

            val resource = ItemIngredient(items, nbt)
            val quantity = buffer.readInt()

            return IngredientConsumeSlot(resource, quantity, slot)
        }

        override fun pack(buffer: PacketByteBuf, slot: IngredientConsumeSlot) {
            val keys = slot.resource.items.map(Registry.ITEM::getId)
            buffer.writeInt(keys.size)

            for (key in keys) {
                buffer.writeIdentifier(key)
            }

            buffer.writeNbt(slot.resource.nbt)
            buffer.writeInt(slot.quantity)
        }

        fun getMatching(json: JsonArray): List<Item> {
            val matching = mutableListOf<Item>()

            for (element in json) {
                val string = element.asString

                if (string.startsWith("#")) {
                    val key = Identifier(string.substring(1))
                    val tag = ServerTagManagerHolder.getTagManager().getTag(Registry.ITEM_KEY, key) { unrecognizedFormat() }

                    matching.addAll(tag.values())
                } else {
                    val key = Identifier(string)
                    val item = Registry.ITEM[key]

                    matching.add(item)
                }
            }

            return matching
        }
    }
}