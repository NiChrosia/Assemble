package assemble.testmod.impl.storage.fluid

import assemble.impl.assembly.adapter.fluid.FluidAdapter
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage
import net.minecraft.fluid.Fluid

@Suppress("UnstableApiUsage")
class SlottedFluidStorage(size: Int, val capacity: Long) : CombinedStorage<FluidVariant, SingleFluidStorage>(
    Array(size) { SingleFluidStorage(capacity) }.toList()
), FluidAdapter {
    override fun getFluid(slot: Int): Fluid {
        return parts[slot].resource.fluid
    }

    override fun getAmount(slot: Int): Long {
        return parts[slot].amount
    }

    override fun getFluidCapacity(slot: Int): Long {
        return capacity
    }

    override fun setFluid(slot: Int, fluid: Fluid) {
        parts[slot].variant = FluidVariant.of(fluid)
    }

    override fun setAmount(slot: Int, amount: Long) {
        parts[slot].amount = amount
    }
}