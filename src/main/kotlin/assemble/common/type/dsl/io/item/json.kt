package assemble.common.type.dsl.io.item

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.ShapedRecipe

val JsonElement.asIngredient: Ingredient
    get() = Ingredient.fromJson(this)

val JsonObject.asItemStack: ItemStack
    get() = ShapedRecipe.outputFromJson(this)

val JsonElement.asItemStack: ItemStack
    get() = asJsonObject.asItemStack