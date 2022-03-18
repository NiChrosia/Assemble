package assemble.testmod.impl.block.entity

import assemble.impl.assembly.adapter.item.InventoryAdapter
import net.minecraft.block.BlockState
import net.minecraft.block.entity.ChestBlockEntity
import net.minecraft.util.math.BlockPos

open class AssembleChestBlockEntity(pos: BlockPos, state: BlockState) : ChestBlockEntity(pos, state), InventoryAdapter