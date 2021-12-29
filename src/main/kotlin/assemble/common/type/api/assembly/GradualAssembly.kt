package assemble.common.type.api.assembly

import assemble.common.type.api.assembly.slot.InputSlot
import assemble.common.type.api.assembly.slot.OutputSlot
import assemble.common.type.api.assembly.slot.gradual.GradualInputSlot
import assemble.common.type.api.assembly.slot.gradual.GradualOutputSlot
import assemble.common.type.api.storage.ProgressInventory
import net.minecraft.util.Identifier

abstract class GradualAssembly<C : ProgressInventory>(
    id: Identifier,
    inputs: List<InputSlot<C, *>>,
    outputs: List<OutputSlot<C, *>>,

    val gradualInputs: List<GradualInputSlot<C, *>>,
    val gradualOutputs: List<GradualOutputSlot<C, *>>,

    val speed: Long,
    val end: Long
) : Assembly<C>(id, inputs, outputs) {
    open fun tick(container: C) {
        if (matches(container)) {
            container.progress += speed

            gradualInputs.forEach { it.consume(container) }
            gradualOutputs.forEach { it.produce(container) }
        }

        if (container.progress >= end) {
            container.progress = 0L

            craft(container)
        }
    }
}