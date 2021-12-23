package assemble.common.content

import assemble.common.Assemble
import nucleus.common.builtin.division.content.ContentCategory

open class AssembleContent(root: Assemble) : ContentCategory<Assemble>(root) {
    val assemblyType = AAssemblyTypeRegistrar(root)
}