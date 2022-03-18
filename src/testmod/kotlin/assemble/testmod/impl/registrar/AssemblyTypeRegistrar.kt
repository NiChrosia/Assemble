package assemble.testmod.impl.registrar

import assemble.api.assembly.Assembly
import assemble.impl.assembly.Transmutation
import assemble.testmod.impl.block.entity.AssembleChestBlockEntity
import nucleus.common.impl.registrar.NamespacedRegistrar

open class AssemblyTypeRegistrar(namespace: String) : NamespacedRegistrar<Assembly.Type<*, out Assembly<*>>>(namespace) {
    val transmutation = register("transmutation", Transmutation.Type<AssembleChestBlockEntity>(0, 1))
}