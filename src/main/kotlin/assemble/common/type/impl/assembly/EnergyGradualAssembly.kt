package assemble.common.type.impl.assembly

import assemble.common.type.api.assembly.GradualAssembly
import assemble.common.type.api.assembly.slot.InputSlot
import assemble.common.type.api.assembly.slot.OutputSlot
import assemble.common.type.api.storage.fluid.EnergyInventory
import assemble.common.type.api.storage.fluid.ProgressInventory
import net.minecraft.util.Identifier

open class EnergyGradualAssembly<C>(
    id: Identifier,
    inputs: List<InputSlot<C, *>>,
    outputs: List<OutputSlot<C, *>>,
    speed: Long,
    end: Long,
    /** Energy consumption per tick. */
    val consumption: Long
) : GradualAssembly<C>(id, inputs, outputs, speed, end) where C : EnergyInventory, C : ProgressInventory {
    override fun matches(container: C): Boolean {
        return super.matches(container).let {
            val aboveZero = container.energy > 0L

            aboveZero && it
        }
    }

    override fun consume(container: C) {
        container.energy -= consumption
    }
}