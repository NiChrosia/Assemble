package assemble.common.type.impl.storage.fluid

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant

interface FluidInventory {
    fun getFluid(slot: Int): FluidVariant
    fun getAmount(slot: Int): Long
    fun getCapacity(slot: Int): Long

    fun setFluid(slot: Int, fluidVariant: FluidVariant)
    fun setAmount(slot: Int, amount: Long)
}