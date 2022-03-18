package assemble.testmod.impl.storage.fluid

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage

@Suppress("UnstableApiUsage")
class SingleFluidStorage(val fluidCapacity: Long) : SingleVariantStorage<FluidVariant>() {
    override fun getBlankVariant(): FluidVariant {
        return FluidVariant.blank()
    }

    override fun getCapacity(variant: FluidVariant): Long {
        if (this.variant != variant) return 0L

        return fluidCapacity - amount
    }
}