package assemble.test.common.type.impl.world.block

import assemble.test.common.TestAssemble
import assemble.test.common.type.impl.world.block.entity.TestBlockEntity
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

open class TestBlock(settings: Settings) : BlockWithEntity(settings) {
    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return TestBlockEntity(pos, state)
    }

    override fun <T : BlockEntity> getTicker(
        world: World,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return checkType(type, TestAssemble.content.blockEntity.test) { tickerWorld, tickerPos, tickerState, entity ->
            entity.tick(tickerWorld, tickerPos, tickerState)
        }
    }
}