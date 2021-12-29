package assemble.common.type.dsl.io.item

import assemble.common.type.impl.stack.IngredientStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Ingredient

fun PacketByteBuf.readIngredient(): Ingredient {
    return Ingredient.fromPacket(this)
}

fun PacketByteBuf.readIngredientStack(): IngredientStack {
    val type = readIngredient()
    val consumption = readInt()

    return IngredientStack(type, consumption)
}

fun PacketByteBuf.writeIngredient(ingredient: Ingredient) {
    ingredient.write(this)
}

fun PacketByteBuf.writeIngredientStack(stack: IngredientStack) {
    writeIngredient(stack.type)
    writeInt(stack.consumption)
}