package assemble.impl.assembly.slot.consume

import assemble.api.assembly.slot.IngredientSlot
import assemble.impl.assembly.adapter.Adapters
import assemble.impl.assembly.adapter.fluid.FluidAdapter
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.fluid.Fluid
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class FluidIngredientSlot<I : FluidAdapter>(override val resource: Fluid, override val quantity: Int, val slot: Int) : IngredientSlot<Fluid, I>() {
    override fun enoughIngredients(inventory: I): Boolean {
        val fluid = inventory.getFluid(slot)
        val amount = inventory.getAmount(slot)

        val correctType = fluid == resource
        val enoughFluid = amount >= quantity

        return correctType && enoughFluid
    }

    override fun consume(inventory: I) {
        inventory.subtractFluid(slot, quantity.toLong())
    }

    class Type<I : FluidAdapter>(val slot: Int) : IngredientSlot.Type<Fluid, I, FluidIngredientSlot<I>>() {
        override fun read(json: JsonElement): FluidIngredientSlot<I> {
            return when(json) {
                is JsonObject -> {
                    val key = Identifier(json["fluid"].asString)

                    val resource = Registry.FLUID[key]
                    val quantity = json["amount"].asInt

                    FluidIngredientSlot(resource, quantity, slot)
                }

                else -> throw unrecognizedFormat(json)
            }
        }

        override fun unpack(buffer: PacketByteBuf): FluidIngredientSlot<I> {
            val key = buffer.readIdentifier()

            val resource = Registry.FLUID[key]
            val quantity = buffer.readInt()

            return FluidIngredientSlot(resource, quantity, slot)
        }

        override fun pack(buffer: PacketByteBuf, slot: FluidIngredientSlot<I>) {
            val key = Registry.FLUID.getId(slot.resource)

            buffer.writeIdentifier(key)
            buffer.writeInt(slot.quantity)
        }
    }
}