package assemble.common.type.impl.assembly.fluid

import assemble.common.type.api.assembly.Assembly
import assemble.common.type.impl.assembly.fluid.slot.FluidInputSlot
import assemble.common.type.impl.assembly.fluid.slot.FluidOutputSlot
import assemble.common.type.impl.storage.fluid.FluidInventory
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.util.Identifier

open class FluidMixingAssembly<C : FluidInventory>(
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