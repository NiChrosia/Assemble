package assemble.test.common.content

import assemble.test.common.TestAssemble
import nucleus.common.builtin.division.content.ContentCategory

open class TestContent(root: TestAssemble) : ContentCategory<TestAssemble>(root) {
    override val block = TABlockRegistrar(root)
    override val blockEntity = TABlockEntityTypeRegistrar(root)
}