package assemble.impl.registrar.template

import assemble.api.assembly.Assembly
import nucleus.common.impl.registrar.NamespacedRegistrar

open class AssemblyTypeRegistrar(namespace: String) : NamespacedRegistrar<Assembly.Type<*, out Assembly<*>>>(namespace)