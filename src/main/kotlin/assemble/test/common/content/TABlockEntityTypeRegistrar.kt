package assemble.test.common.content

import assemble.test.common.TestAssemble
import assemble.test.common.type.impl.world.block.entity.TestBlockEntity
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage
import nucleus.common.builtin.division.content.BlockEntityTypeRegistrar

open class TABlockEntityTypeRegistrar(root: TestAssemble) : BlockEntityTypeRegistrar<TestAssemble>(root) {
    val test by memberOf(root.identify("test")) { typeOf(::TestBlockEntity, root.content.block.test) }
}