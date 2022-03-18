package assemble.impl.assembly.slot

import assemble.api.assembly.Assembly
import assemble.impl.assembly.adapter.ItemAdapter
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

open class ItemSlot(override val resource: Item, override val quantity: Int, val slot: Int) : Assembly.Slot<Item, ItemAdapter>() {
    override fun spaceAvailable(inventory: ItemAdapter): Boolean {
        val stack = inventory.getStack(slot)

        val empty = stack.isEmpty
        val sameType = stack.item == resource
        val hasSpace = stack.count <= stack.maxCount - quantity

        return empty || (sameType && hasSpace)
    }

    override fun enoughIngredients(inventory: ItemAdapter): Boolean {
        val stack = inventory.getStack(slot)

        val validType = stack.item == resource
        val enoughItems = stack.count - quantity >= 0

        return validType && enoughItems
    }

    override fun craft(inventory: ItemAdapter) {
        val stack = inventory.getStack(slot)
        val new = ItemStack(resource, stack.count + quantity)

        inventory.setStack(slot, new)
    }

    override fun consume(inventory: ItemAdapter) {
        val stack = inventory.getStack(slot)
        val new = ItemStack(resource, stack.count - quantity)

        inventory.setStack(slot, new)
    }

    class Type(val slot: Int) : Assembly.Slot.Type<Item, ItemAdapter, ItemSlot>() {
        override fun read(json: JsonElement): ItemSlot {
            return when(json) {
                is JsonPrimitive -> {
                    if (json.isString) {
                        val key = Identifier(json.asString)
                        val item = Registry.ITEM[key]

                        ItemSlot(item, 1, slot)
                    } else if (json.isNumber) {
                        val key = json.asNumber.toInt()
                        val item = Registry.ITEM[key]

                        ItemSlot(item, 1, slot)
                    } else {
                        throw unrecognizedFormat()
                    }
                }

                is JsonObject -> {
                    val raw = json["item"] ?: throw missingEntry("item")
                    val key = Identifier(raw.asString)
                    val item = Registry.ITEM[key]

                    val count = json["count"]?.asInt ?: 1

                    ItemSlot(item, count, slot)
                }

                else -> throw unrecognizedFormat()
            }
        }

        override fun unpack(buffer: PacketByteBuf): ItemSlot {
            val key = buffer.readIdentifier()
            val item = Registry.ITEM[key]

            val count = buffer.readInt()

            return ItemSlot(item, count, slot)
        }

        override fun pack(buffer: PacketByteBuf, slot: ItemSlot) {
            val key = Registry.ITEM.getId(slot.resource)
            buffer.writeIdentifier(key)

            val count = slot.quantity
            buffer.writeInt(count)
        }
    }
}