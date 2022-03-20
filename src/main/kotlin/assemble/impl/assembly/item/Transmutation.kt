package assemble.impl.assembly.item

import assemble.api.assembly.Assembly
import assemble.impl.assembly.adapter.item.ItemAdapter
import assemble.impl.assembly.slot.consume.ItemIngredientSlot
import assemble.impl.assembly.slot.craft.ItemResultSlot
import com.google.gson.JsonObject
import net.minecraft.network.PacketByteBuf

class Transmutation<I : ItemAdapter>(
    val ingredient: ItemIngredientSlot<I>,
    val result: ItemResultSlot<I>
) : Assembly<I>(listOf(ingredient), listOf(result)) {
    class Type<I : ItemAdapter>(ingredientSlot: Int, resultSlot: Int) : Assembly.Type<I, Transmutation<I>>() {
        val ingredient = ItemIngredientSlot.Type<I>(ingredientSlot)
        val result = ItemResultSlot.Type<I>(resultSlot)
        
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