package assemble.common.type.api.assembly

import assemble.common.type.api.assembly.slot.InputSlot
import assemble.common.type.api.assembly.slot.OutputSlot
import assemble.common.type.api.storage.fluid.ProgressInventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier

abstract class GradualAssembly<C : ProgressInventory>(
    id: Identifier,
    inputs: List<InputSlot<C, *>>,
    outputs: List<OutputSlot<C, *>>,
    val speed: Long,
    val end: Long
) : Assembly<C>(id, inputs, outputs) {
    abstract fun consume(container: C)

    open fun tick(container: C) {
        if (matches(container)) {
            container.progress += speed

            consume(container)
        }

        if (container.progress >= end) {
            container.progress = 0L

            craft(container)
        }
    }
}