package assemble.impl.assembly.slot.craft

import assemble.api.assembly.slot.ResultSlot
import assemble.impl.assembly.adapter.item.ItemAdapter
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import net.minecraft.item.Item
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

open class ItemResultSlot<I : ItemAdapter>(override val resource: Item, override val quantity: Int, val slot: Int) : ResultSlot<Item, I>() {
    override fun spaceAvailable(inventory: I): Boolean {
        val item = inventory.getItem(slot)
        val count = inventory.getCount(slot)
        val max = inventory.getItemCapacity(slot)

        val empty = count <= 0
        val sameType = item == resource
        val hasSpace = count <= max - quantity

        return empty || (sameType && hasSpace)
    }

    override fun craft(inventory: I) {
        inventory.setItem(slot, resource)
        inventory.addItems(slot, quantity)
    }

    class Type<I : ItemAdapter>(val slot: Int) : ResultSlot.Type<Item, I, ItemResultSlot<I>>() {
        override fun read(json: JsonElement): ItemResultSlot<I> {
            return when(json) {
                is JsonPrimitive -> {
                    if (json.isString) {
                        val key = Identifier(json.asString)
                        val item = Registry.ITEM[key]

                        ItemResultSlot(item, 1, slot)
                    } else {
                        throw unrecognizedFormat(json)
                    }
                }

                is JsonObject -> {
                    val raw = json["item"] ?: throw missingEntry("item")
                    val key = Identifier(raw.asString)
                    val item = Registry.ITEM[key]

                    val count = json["count"]?.asInt ?: 1

                    ItemResultSlot(item, count, slot)
                }

                else -> throw unrecognizedFormat(json)
            }
        }

        override fun unpack(buffer: PacketByteBuf): ItemResultSlot<I> {
            val key = buffer.readIdentifier()
            val item = Registry.ITEM[key]

            val count = buffer.readInt()

            return ItemResultSlot(item, count, slot)
        }

        override fun pack(buffer: PacketByteBuf, slot: ItemResultSlot<I>) {
            val key = Registry.ITEM.getId(slot.resource)
            buffer.writeIdentifier(key)

            val count = slot.quantity
            buffer.writeInt(count)
        }
    }
}