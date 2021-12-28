package assemble.common.type.impl.assembly.slot.item

import assemble.common.type.api.assembly.slot.InputSlot
import net.minecraft.inventory.Inventory
import net.minecraft.recipe.Ingredient

open class ItemInputSlot<C : Inventory>(val slot: Int, val type: Ingredient) : InputSlot<C, Ingredient>() {
    override fun matches(container: C): Boolean {
        return type.test(container.getStack(slot))
    }

    override fun craft(container: C) {
        val stack = container.getStack(slot)
        stack.decrement(1)
    }
}