package assemble.common.type.impl.assembly.fluid

import assemble.common.type.api.assembly.Assembly
import assemble.common.type.impl.assembly.slot.fluid.FluidInputSlot
import assemble.common.type.impl.assembly.slot.fluid.FluidOutputSlot
import assemble.common.type.api.storage.fluid.MultiFluidInventory
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.util.Identifier

open class FluidMixingAssembly<C : MultiFluidInventory> @JvmOverloads constructor(
    id: Identifier,

    val first: FluidVariant,
    val firstAmount: Long,

    val second: FluidVariant,
    val secondAmount: Long,

    val result: FluidVariant,
    val resultAmount: Long,

    val slots: List<Int> = listOf(0, 1, 2)
) : Assembly<C>(
    id,
    listOf(
        FluidInputSlot(slots[0], first, firstAmount),
        FluidInputSlot(slots[1], second, secondAmount)
    ),
    listOf(
        FluidOutputSlot(slots[2], result, resultAmount)
    )
)