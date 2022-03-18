package assemble.impl.assembly.slot.consume

import assemble.api.assembly.slot.ConsumeSlot
import assemble.impl.assembly.adapter.fluid.FluidAdapter
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.fluid.Fluid
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class FluidConsumeSlot(override val resource: Fluid, override val quantity: Int, val slot: Int) : ConsumeSlot<Fluid, FluidAdapter>() {
    override fun enoughIngredients(inventory: FluidAdapter): Boolean {
        val fluid = inventory.getFluid(slot)
        val amount = inventory.getAmount(slot)

        val correctType = fluid == resource
        val enoughFluid = amount >= quantity

        return correctType && enoughFluid
    }

    override fun consume(inventory: FluidAdapter) {
        val amount = inventory.getAmount(slot)
        inventory.setAmount(slot, amount - quantity)
    }

    class Type(val slot: Int) : ConsumeSlot.Type<Fluid, FluidAdapter, FluidConsumeSlot>() {
        override fun read(json: JsonElement): FluidConsumeSlot {
            return when(json) {
                is JsonObject -> {
                    val key = Identifier(json["fluid"].asString)

                    val resource = Registry.FLUID[key]
                    val quantity = json["amount"].asInt

                    FluidConsumeSlot(resource, quantity, slot)
                }

                else -> throw unrecognizedFormat()
            }
        }

        override fun unpack(buffer: PacketByteBuf): FluidConsumeSlot {
            val key = buffer.readIdentifier()

            val resource = Registry.FLUID[key]
            val quantity = buffer.readInt()

            return FluidConsumeSlot(resource, quantity, slot)
        }

        override fun pack(buffer: PacketByteBuf, slot: FluidConsumeSlot) {
            val key = Registry.FLUID.getId(slot.resource)

            buffer.writeIdentifier(key)
            buffer.writeInt(slot.quantity)
        }
    }
}