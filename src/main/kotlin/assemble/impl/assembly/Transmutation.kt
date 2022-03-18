package assemble.impl.assembly

import assemble.api.assembly.Assembly
import assemble.impl.assembly.adapter.ItemAdapter
import assemble.impl.assembly.slot.ItemSlot
import com.google.gson.JsonObject
import net.minecraft.network.PacketByteBuf

class Transmutation<I : ItemAdapter>(
    val ingredient: ItemSlot,
    val result: ItemSlot
) : Assembly<I>(listOf(ingredient), listOf(result)) {
    class Type<I : ItemAdapter>(ingredientSlot: Int, resultSlot: Int) : Assembly.Type<I, Transmutation<I>>() {
        val ingredient = ItemSlot.Type(ingredientSlot)
        val result = ItemSlot.Type(resultSlot)
        
        override fun read(json: JsonObject): Transmutation<I> {
            val ingredient = ingredient.read(json["ingredient"])
            val result = result.read(json["result"])

            return Transmutation(ingredient, result)
        }

        override fun unpack(buffer: PacketByteBuf): Transmutation<I> {
            val ingredient = ingredient.unpack(buffer)
            val result = result.unpack(buffer)

            return Transmutation(ingredient, result)
        }

        override fun pack(buffer: PacketByteBuf, assembly: Transmutation<I>) {
            ingredient.pack(buffer, assembly.ingredient)
            result.pack(buffer, assembly.result)
        }
    }
}