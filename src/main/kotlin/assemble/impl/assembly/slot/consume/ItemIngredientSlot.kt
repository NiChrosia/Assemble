package assemble.impl.assembly.slot.consume

import assemble.api.assembly.slot.IngredientSlot
import assemble.impl.assembly.adapter.item.ItemAdapter
import assemble.impl.assembly.resource.ItemIngredient
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.mojang.serialization.JsonOps
import net.minecraft.item.Item
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtOps
import net.minecraft.network.PacketByteBuf
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryEntry

class ItemIngredientSlot<I : ItemAdapter>(override val resource: ItemIngredient, override val quantity: Int, val slot: Int) : IngredientSlot<ItemIngredient, I>() {
    override fun enoughIngredients(inventory: I): Boolean {
        val item = inventory.getItem(slot)
        val nbt = inventory.getNbt(slot)
        val count = inventory.getCount(slot)

        val validType = resource.matches(item)
        val correctNbt = resource.matches(nbt)
        val enoughItems = count >= quantity

        return validType && correctNbt && enoughItems
    }

    override fun consume(inventory: I) {
        inventory.subtractItems(slot, quantity)
    }

    class Type<I : ItemAdapter>(val slot: Int) : IngredientSlot.Type<ItemIngredient, I, ItemIngredientSlot<I>>() {
        override fun read(json: JsonElement): ItemIngredientSlot<I> {
            return when(json) {
                is JsonPrimitive -> {
                    if (json.isString) {
                        val matching = getMatching(json.asString)
                        val resource = ItemIngredient(matching)

                        ItemIngredientSlot(resource, 1, slot)
                    } else {
                        throw unrecognizedFormat(json)
                    }
                }

                is JsonArray -> {
                    val matching = getAllMatching(json)
                    val resource = ItemIngredient(matching)

                    ItemIngredientSlot(resource, 1, slot)
                }

                is JsonObject -> {
                    val matching = when(json["matching"]) {
                        is JsonArray -> getAllMatching(json["matching"].asJsonArray)
                        is JsonPrimitive -> getMatching(json["matching"].asString)
                        else -> throw unrecognizedFormat(json)
                    }

                    val nbt = JsonOps.INSTANCE.convertTo(NbtOps.INSTANCE, json["nbt"])
                    val compound = (nbt as? NbtCompound) ?: NbtCompound()

                    val quantity = json["count"]?.asInt ?: 1
                    val resource = ItemIngredient(matching, compound)

                    ItemIngredientSlot(resource, quantity, slot)
                }

                else -> throw unrecognizedFormat(json)
            }
        }

        override fun unpack(buffer: PacketByteBuf): ItemIngredientSlot<I> {
            val keysSize = buffer.readInt()
            val keys = mutableListOf<Identifier>()

            for (i in 1..keysSize) {
                keys.add(buffer.readIdentifier())
            }

            val items = keys.map(Registry.ITEM::get)
            val nbt = buffer.readUnlimitedNbt() ?: NbtCompound()

            val resource = ItemIngredient(items, nbt)
            val quantity = buffer.readInt()

            return ItemIngredientSlot(resource, quantity, slot)
        }

        override fun pack(buffer: PacketByteBuf, slot: ItemIngredientSlot<I>) {
            val keys = slot.resource.items.map(Registry.ITEM::getId)
            buffer.writeInt(keys.size)

            for (key in keys) {
                buffer.writeIdentifier(key)
            }

            buffer.writeNbt(slot.resource.nbt)
            buffer.writeInt(slot.quantity)
        }

        fun getAllMatching(json: JsonArray): List<Item> {
            val strings = json.map(JsonElement::getAsString)
            val values = strings.map(::getMatching)

            return values.flatten()
        }

        fun getMatching(string: String): List<Item> {
            return if (string.startsWith("#")) {
                val key = Identifier(string.substring(1))
                val tag = TagKey.of(Registry.ITEM_KEY, key)!!
                val entries = Registry.ITEM.iterateEntries(tag)

                entries.map(RegistryEntry<Item>::value)
            } else {
                val key = Identifier(string)
                val item = Registry.ITEM[key]

                listOf(item)
            }
        }
    }
}