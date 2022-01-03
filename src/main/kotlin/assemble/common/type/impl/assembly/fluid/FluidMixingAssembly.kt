package assemble.common.type.impl.assembly.fluid

import assemble.common.type.api.assembly.Assembly
import assemble.common.type.impl.assembly.slot.fluid.FluidInput
import assemble.common.type.impl.assembly.slot.fluid.FluidOutput
import assemble.common.type.api.storage.fluid.FluidStorageInventory
import assemble.common.type.impl.stack.FluidStack
import net.minecraft.util.Identifier

open class FluidMixingAssembly<C : FluidStorageInventory> @JvmOverloads constructor(
    id: Identifier,

    val first: FluidStack,
    val second: FluidStack,
    val result: FluidStack,

    val slots: List<Int> = listOf(0, 1, 2)
) : Assembly<C>(
    id,
    listOf(
        FluidInput(slots[0], first),
        FluidInput(slots[1], second)
    ),
    listOf(
        FluidOutput(slots[2], result)
    )
)