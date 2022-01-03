package assemble.test.common.content

import assemble.test.common.AssembleTest
import nucleus.common.builtin.division.content.ContentCategory

class TestContent(root: AssembleTest) : ContentCategory<AssembleTest>(root) {
    override val block = TABlockRegistrar(root)
    override val blockEntity = TABlockEntityTypeRegistrar(root)
    val assemblyType = TAAssemblyTypeRegistrar(root)
}