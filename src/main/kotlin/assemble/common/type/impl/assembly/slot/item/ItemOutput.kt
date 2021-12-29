package assemble.common.type.impl.assembly.slot.item

import assemble.common.type.api.assembly.slot.OutputSlot
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack

open class ItemOutput<C : Inventory>(val slot: Int, val result: ItemStack) : OutputSlot<C, ItemStack>() {
    override fun matches(container: C): Boolean {
        val stack = container.getStack(slot)

        val correctType = stack.isEmpty || stack.item == result.item
        val notFull = stack.count < stack.maxCount - result.count

        return correctType && notFull
    }

    override fun produce(container: C) {
        val stack = container.getStack(slot)

        if (stack.isEmpty) {
            container.setStack(slot, result.copy())
        } else {
            stack.increment(result.count)
        }
    }
}