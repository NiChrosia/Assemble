package assemble.impl.assembly.fluid

import assemble.api.assembly.Assembly
import assemble.impl.assembly.adapter.fluid.FluidAdapter
import assemble.impl.assembly.slot.consume.FluidIngredientSlot
import assemble.impl.assembly.slot.craft.FluidResultSlot
import com.google.gson.JsonObject
import net.minecraft.network.PacketByteBuf

class FluidTransmutation<I : FluidAdapter>(
    val ingredient: FluidIngredientSlot<I>,
    val result: FluidResultSlot<I>
) : Assembly<I>(listOf(ingredient), listOf(result)) {
    class Type<I : FluidAdapter>(ingredientSlot: Int, resultSlot: Int) : Assembly.Type<I, FluidTransmutation<I>>() {
        val ingredient = FluidIngredientSlot.Type<I>(ingredientSlot)
        val result = FluidResultSlot.Type<I>(resultSlot)

        override fun read(json: JsonObject): FluidTransmutation<I> {
            val ingredient = ingredient.read(json["ingredient"])
            val result = result.read(json["result"])

            return FluidTransmutation(ingredient, result)
        }

        override fun unpack(buffer: PacketByteBuf): FluidTransmutation<I> {
            val ingredient = ingredient.unpack(buffer)
            val result = result.unpack(buffer)

            return FluidTransmutation(ingredient, result)
        }

        override fun pack(buffer: PacketByteBuf, assembly: FluidTransmutation<I>) {
            ingredient.pack(buffer, assembly.ingredient)
            result.pack(buffer, assembly.result)
        }
    }
}