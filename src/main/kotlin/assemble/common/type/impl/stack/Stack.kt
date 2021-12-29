package assemble.common.type.impl.stack

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.recipe.Ingredient

data class Stack<T>(val type: T, val amount: Long)

typealias FluidStack = Stack<FluidVariant>
typealias IngredientStack = Stack<Ingredient>