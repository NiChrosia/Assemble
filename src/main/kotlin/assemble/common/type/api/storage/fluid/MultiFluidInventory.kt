package assemble.common.type.api.storage.fluid

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage

interface MultiFluidInventory {
    val fluidStorage: CombinedStorage<FluidVariant, SingleSlotStorage<FluidVariant>>
}