package assemble.common.type.api.registrar

import assemble.common.Assemble
import assemble.common.type.api.assembly.Assembly
import assemble.common.type.api.assembly.type.AssemblyType
import net.minecraft.inventory.Inventory
import net.minecraft.util.Identifier
import nucleus.common.builtin.division.ModRoot
import nucleus.common.member.Member
import nucleus.common.registrar.Registrar

open class AssemblyTypeRegistrar<R : ModRoot<R>>(root: R) : Registrar<Identifier, AssemblyType<*, Assembly<*>>, R>(root) {
    override fun register(key: Identifier, value: AssemblyType<*, Assembly<*>>): AssemblyType<*, Assembly<*>> {
        return super.register(key, value).also {
            Assemble.manager.types[key] = it
        }
    }
}