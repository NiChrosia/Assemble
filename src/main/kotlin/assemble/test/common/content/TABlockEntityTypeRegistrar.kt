package assemble.test.common.content

import assemble.test.common.AssembleTest
import assemble.test.common.type.impl.world.block.entity.TestBlockEntity
import nucleus.common.builtin.division.content.BlockEntityTypeRegistrar

open class TABlockEntityTypeRegistrar(root: AssembleTest) : BlockEntityTypeRegistrar<AssembleTest>(root) {
    val test by memberOf(root.identify("test")) { typeOf(::TestBlockEntity, root.content.block.test) }
}