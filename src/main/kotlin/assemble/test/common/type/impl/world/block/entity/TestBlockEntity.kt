package assemble.test.common.type.impl.world.block.entity

import assemble.common.Assemble
import assemble.test.common.AssembleTest
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

open class TestBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(AssembleTest.content.blockEntity.test, pos, state), Inventory by SimpleInventory(2) {
    open val assemblyType = AssembleTest.content.assemblyType.itemProcessing

    override fun markDirty() {
        super.markDirty()
    }

    open fun tick(world: World, pos: BlockPos, state: BlockState) {
        val assembly = Assemble.matching(assemblyType).first()

        if (assembly.matches(this)) {
            assembly.craft(this)
        }
    }
}