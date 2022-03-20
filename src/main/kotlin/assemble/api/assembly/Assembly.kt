package assemble.api.assembly

import assemble.api.assembly.slot.IngredientSlot
import assemble.api.assembly.slot.ResultSlot
import com.google.gson.JsonObject
import net.minecraft.network.PacketByteBuf

abstract class Assembly<I>(val ingredients: List<IngredientSlot<*, I>>, val results: List<ResultSlot<*, I>>) {
    open fun matches(inventory: I): Boolean {
        return ingredients.all { it.enoughIngredients(inventory) } && results.all { it.spaceAvailable(inventory) }
    }

    open fun craft(inventory: I) {
        for (ingredient in ingredients) ingredient.consume(inventory)
        for (result in results) result.craft(inventory)
    }

    abstract class Type<I, A : Assembly<I>> {
        abstract fun read(json: JsonObject): A

        abstract fun unpack(buffer: PacketByteBuf): A
        abstract fun pack(buffer: PacketByteBuf, assembly: A)
    }
}