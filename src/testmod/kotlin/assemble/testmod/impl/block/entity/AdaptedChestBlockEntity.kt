package assemble.testmod.impl.block.entity

import assemble.impl.assembly.adapter.item.InventoryAdapter
import assemble.testmod.impl.storage.fluid.SlottedFluidStorage
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.minecraft.block.BlockState
import net.minecraft.block.entity.ChestBlockEntity
import net.minecraft.util.math.BlockPos

@Suppress("UnstableApiUsage")
open class AdaptedChestBlockEntity(pos: BlockPos, state: BlockState) : ChestBlockEntity(pos, state), InventoryAdapter {
    val fluidStorage = SlottedFluidStorage(2, FluidConstants.BUCKET)
}