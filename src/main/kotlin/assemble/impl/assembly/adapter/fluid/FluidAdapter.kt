package assemble.impl.assembly.adapter.fluid

import net.minecraft.fluid.Fluid

interface FluidAdapter {
    fun getFluid(slot: Int): Fluid
    fun getAmount(slot: Int): Long
    fun getMaxAmount(slot: Int): Long

    fun setFluid(slot: Int, fluid: Fluid)
    fun setAmount(slot: Int, amount: Long)
}