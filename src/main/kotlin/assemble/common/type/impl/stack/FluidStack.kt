package assemble.common.type.impl.stack

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant

data class FluidStack(val type: FluidVariant, val amount: Long)