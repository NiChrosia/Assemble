package assemble.common.type.dsl.io.item

import com.google.gson.JsonObject
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.ShapedRecipe

val JsonObject.asIngredient: Ingredient
    get() = Ingredient.fromJson(this)

val JsonObject.asItemStack: ItemStack
    get() = ShapedRecipe.outputFromJson(this)