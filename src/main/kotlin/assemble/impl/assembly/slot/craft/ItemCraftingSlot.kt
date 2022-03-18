package assemble.impl.assembly.slot.craft

import assemble.api.assembly.slot.CraftingSlot
import assemble.impl.assembly.adapter.item.ItemAdapter
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import net.minecraft.item.Item
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

open class ItemCraftingSlot(override val resource: Item, override val quantity: Int, val slot: Int) : CraftingSlot<Item, ItemAdapter>() {
    override fun spaceAvailable(inventory: ItemAdapter): Boolean {
        val item = inventory.getItem(slot)
        val count = inventory.getCount(slot)
        val max = inventory.getMaxCount(slot)

        val empty = count <= 0
        val sameType = item == resource
        val hasSpace = count <= max - quantity

        return empty || (sameType && hasSpace)
    }

    override fun craft(inventory: ItemAdapter) {
        val count = inventory.getCount(slot)

        inventory.setItem(slot, resource)
        inventory.setCount(slot, count + quantity)
    }

    class Type(val slot: Int) : CraftingSlot.Type<Item, ItemAdapter, ItemCraftingSlot>() {
        override fun read(json: JsonElement): ItemCraftingSlot {
            return when(json) {
                is JsonPrimitive -> {
                    if (json.isString) {
                        val key = Identifier(json.asString)
                        val item = Registry.ITEM[key]

                        ItemCraftingSlot(item, 1, slot)
                    } else {
                        throw unrecognizedFormat()
                    }
                }

                is JsonObject -> {
                    val raw = json["item"] ?: throw missingEntry("item")
                    val key = Identifier(raw.asString)
                    val item = Registry.ITEM[key]

                    val count = json["count"]?.asInt ?: 1

                    ItemCraftingSlot(item, count, slot)
                }

                else -> throw unrecognizedFormat()
            }
        }

        override fun unpack(buffer: PacketByteBuf): ItemCraftingSlot {
            val key = buffer.readIdentifier()
            val item = Registry.ITEM[key]

            val count = buffer.readInt()

            return ItemCraftingSlot(item, count, slot)
        }

        override fun pack(buffer: PacketByteBuf, slot: ItemCraftingSlot) {
            val key = Registry.ITEM.getId(slot.resource)
            buffer.writeIdentifier(key)

            val count = slot.quantity
            buffer.writeInt(count)
        }
    }
}