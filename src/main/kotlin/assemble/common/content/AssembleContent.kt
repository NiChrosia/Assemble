package assemble.common.content

import assemble.common.Assemble
import nucleus.common.builtin.division.content.ContentCategory

class AssembleContent(root: Assemble) : ContentCategory<Assemble>(root) {
    val slotSerializer = AAssemblySerializerRegistrar(root)
}