package assemble.testmod.impl.registrar

import assemble.testmod.AssembleTest
import assemble.testmod.impl.block.entity.TestBlockEntity
import nucleus.common.impl.registrar.template.BlockEntityTypeRegistrar

class TBlockEntityTypeRegistrar(namespace: String) : BlockEntityTypeRegistrar(namespace) {
    val test = register("test", typeOf(AssembleTest.blocks.test, ::TestBlockEntity))
}