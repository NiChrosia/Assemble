package assemble.common.type.api.assembly

import assemble.common.type.api.assembly.slot.InputSlot
import assemble.common.type.api.assembly.slot.OutputSlot
import assemble.common.type.impl.assembly.slot.fluid.FluidOutputSlot
import assemble.common.type.impl.assembly.slot.item.ItemInputSlot
import net.minecraft.util.Identifier

/** The core of the crafting system. Inputs & outputs are registered via their respective properties, created using
 * slots like [ItemInputSlot], [FluidOutputSlot], etc. */
abstract class Assembly<C>(
    /** The ID of the assembly. Used to distinguish assemblies of the same type. */
    val id: Identifier,
    /** The inputs of this assembly, used to consume items & determine validity. */
    val inputs: List<InputSlot<C, *>>,
    /** The outputs of this assembly, used to produce results & determine validity. */
    val outputs: List<OutputSlot<C, *>>
) {
    open fun matches(container: C): Boolean {
        val inputs = inputs.all { it.matches(container) }
        val outputs = outputs.all { it.matches(container) }

        return inputs && outputs
    }

    open fun craft(container: C) {
        inputs.forEach { it.craft(container) }
        outputs.forEach { it.craft(container) }
    }
}