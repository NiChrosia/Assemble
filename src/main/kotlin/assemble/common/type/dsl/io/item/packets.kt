package assemble.common.type.dsl.io.item

import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Ingredient

fun PacketByteBuf.readIngredient(): Ingredient {
    return Ingredient.fromPacket(this)
}

fun PacketByteBuf.writeIngredient(ingredient: Ingredient) {
    ingredient.write(this)
}