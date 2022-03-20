package assemble.testmod.impl.block

import assemble.testmod.AssembleTest
import assemble.testmod.impl.block.entity.TestBlockEntity
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.ItemPlacementContext
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class TestBlock(settings: Settings) : BlockWithEntity(settings) {
    val ticker = BlockEntityTicker<TestBlockEntity> { world, pos, state, entity -> entity.tick(world, pos, state) }

    override fun getRenderType(state: BlockState): BlockRenderType {
        return BlockRenderType.MODEL
    }

    @Suppress("NAME_SHADOWING")
    override fun <T : BlockEntity> getTicker(
        world: World,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return checkType(type, AssembleTest.blockEntityTypes.test, ticker)
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): TestBlockEntity {
        return TestBlockEntity(pos, state)
    }
}