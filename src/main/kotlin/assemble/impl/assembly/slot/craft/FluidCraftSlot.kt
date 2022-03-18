package assemble.impl.assembly.slot.craft

import assemble.api.assembly.slot.CraftSlot
import assemble.impl.assembly.adapter.fluid.FluidAdapter
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class FluidCraftSlot(override val resource: Fluid, override val quantity: Int, val slot: Int) : CraftSlot<Fluid, FluidAdapter>() {
    override fun spaceAvailable(inventory: FluidAdapter): Boolean {
        val fluid = inventory.getFluid(slot)
        val amount = inventory.getAmount(slot)
        val max = inventory.getMaxAmount(slot)

        val empty = fluid === Fluids.EMPTY || amount <= 0
        val sameType = fluid == resource
        val hasSpace = amount <= max - quantity

        return empty || (sameType && hasSpace)
    }

    override fun craft(inventory: FluidAdapter) {
        val amount = inventory.getAmount(slot)

        inventory.setFluid(slot, resource)
        inventory.setAmount(slot, amount + quantity)
    }

    class Type(val slot: Int) : CraftSlot.Type<Fluid, FluidAdapter, FluidCraftSlot>() {
        override fun read(json: JsonElement): FluidCraftSlot {
            return when(json) {
                is JsonObject -> {
                    val key = Identifier(json["fluid"].asString)

                    val resource = Registry.FLUID[key]
                    val quantity = json["amount"].asInt

                    FluidCraftSlot(resource, quantity, slot)
                }

                else -> throw unrecognizedFormat()
            }
        }

        override fun unpack(buffer: PacketByteBuf): FluidCraftSlot {
            val key = buffer.readIdentifier()

            val resource = Registry.FLUID[key]
            val quantity = buffer.readInt()

            return FluidCraftSlot(resource, quantity, slot)
        }

        override fun pack(buffer: PacketByteBuf, slot: FluidCraftSlot) {
            val key = Registry.FLUID.getId(slot.resource)

            buffer.writeIdentifier(key)
            buffer.writeInt(slot.quantity)
        }
    }
}