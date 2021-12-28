package assemble.test.common.content

import assemble.common.type.api.registrar.AssemblyTypeRegistrar
import assemble.common.type.impl.assembly.type.item.ItemMergingType
import assemble.common.type.impl.assembly.type.item.ItemProcessingType
import assemble.test.common.TestAssemble
import assemble.test.common.type.impl.world.block.entity.TestBlockEntity

open class TAAssemblyTypeRegistrar(root: TestAssemble) : AssemblyTypeRegistrar<TestAssemble>(root) {
    val itemMerging by memberOf(root.identify("item_merging")) { ItemMergingType<TestBlockEntity>(it) }
    val itemProcessing by memberOf(root.identify("item_processing")) { ItemProcessingType<TestBlockEntity>(it) }
}