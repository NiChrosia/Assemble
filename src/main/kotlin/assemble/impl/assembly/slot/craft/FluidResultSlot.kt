package assemble.impl.assembly.slot.craft

import assemble.api.assembly.slot.ResultSlot
import assemble.impl.assembly.adapter.fluid.FluidAdapter
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class FluidResultSlot<I : FluidAdapter>(override val resource: Fluid, override val quantity: Int, val slot: Int) : ResultSlot<Fluid, I>() {
    override fun spaceAvailable(inventory: I): Boolean {
        val fluid = inventory.getFluid(slot)
        val amount = inventory.getAmount(slot)
        val max = inventory.getFluidCapacity(slot)

        val empty = fluid === Fluids.EMPTY || amount <= 0
        val sameType = fluid == resource
        val hasSpace = amount <= max - quantity

        return empty || (sameType && hasSpace)
    }

    override fun craft(inventory: I) {
        inventory.setFluid(slot, resource)
        inventory.addFluid(slot, quantity.toLong())
    }

    class Type<I : FluidAdapter>(val slot: Int) : ResultSlot.Type<Fluid, I, FluidResultSlot<I>>() {
        @Suppress("UnstableApiUsage")
        override fun read(json: JsonElement): FluidResultSlot<I> {
            return when(json) {
                is JsonPrimitive -> {
                    if (json.isString) {
                        val key = Identifier(json.asString)

                        val resource = Registry.FLUID[key]
                        val quantity = FluidConstants.BUCKET.toInt()

                        FluidResultSlot(resource, quantity, slot)
                    } else {
                        throw unrecognizedFormat(json)
                    }
                }

                is JsonObject -> {
                    val key = Identifier(json["fluid"].asString)

                    val resource = Registry.FLUID[key]
                    val quantity = json["amount"].asInt

                    FluidResultSlot(resource, quantity, slot)
                }

                else -> throw unrecognizedFormat(json)
            }
        }

        override fun unpack(buffer: PacketByteBuf): FluidResultSlot<I> {
            val key = buffer.readIdentifier()

            val resource = Registry.FLUID[key]
            val quantity = buffer.readInt()

            return FluidResultSlot(resource, quantity, slot)
        }

        override fun pack(buffer: PacketByteBuf, slot: FluidResultSlot<I>) {
            val key = Registry.FLUID.getId(slot.resource)

            buffer.writeIdentifier(key)
            buffer.writeInt(slot.quantity)
        }
    }
}