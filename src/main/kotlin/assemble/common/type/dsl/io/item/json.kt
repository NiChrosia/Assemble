package assemble.common.type.dsl.io.item

import assemble.common.type.impl.stack.IngredientStack
import com.google.gson.JsonObject
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.ShapedRecipe

val JsonObject.asIngredient: Ingredient
    get() = Ingredient.fromJson(this)

val JsonObject.asIngredientStack: IngredientStack
    get() {
        val type = asIngredient
        val consumption = this["consumption"].asLong

        return IngredientStack(type, consumption)
    }

val JsonObject.asItemStack: ItemStack
    get() = ShapedRecipe.outputFromJson(this)