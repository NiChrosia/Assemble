package assemble.impl.assembly.adapter.fluid

import assemble.impl.assembly.adapter.Adapters
import net.minecraft.fluid.Fluid

interface FluidAdapter {
    fun getFluid(slot: Int): Fluid
    fun getAmount(slot: Int): Long
    fun getFluidCapacity(slot: Int): Long

    fun setFluid(slot: Int, fluid: Fluid)
    fun setAmount(slot: Int, amount: Long)

    fun addFluid(slot: Int, amount: Long) {
        Adapters.add(this::getAmount, this::setAmount, slot, amount)
    }

    fun subtractFluid(slot: Int, amount: Long) {
        Adapters.add(this::getAmount, this::setAmount, slot, amount)
    }
}