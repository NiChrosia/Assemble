package assemble.impl.registrar.template

import assemble.Assemble
import assemble.api.assembly.Assembly
import net.minecraft.util.Identifier
import nucleus.common.api.registrar.capability.Namespaced
import nucleus.common.impl.registrar.DeferredRegistrar

open class AssemblyTypeRegistrar(override val namespace: String) : DeferredRegistrar<Identifier, Assembly.Type<*, out Assembly<*>>>(Assemble.loader.types::content), Namespaced<Assembly.Type<*, out Assembly<*>>>