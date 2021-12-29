package assemble.common.type.impl.assembly.slot.item

import assemble.common.type.api.assembly.slot.InputSlot
import assemble.common.type.impl.stack.IngredientStack
import net.minecraft.inventory.Inventory
import net.minecraft.recipe.Ingredient

open class ItemInput<C : Inventory>(val slot: Int, val stack: IngredientStack) : InputSlot<C, Ingredient>() {
    override fun matches(container: C): Boolean {
        val slotStack = container.getStack(slot)

        val matches = stack.type.test(slotStack)
        val enoughItems = slotStack.count >= stack.consumption

        return matches && enoughItems
    }

    override fun consume(container: C) {
        container.getStack(slot).decrement(stack.consumption)
    }
}