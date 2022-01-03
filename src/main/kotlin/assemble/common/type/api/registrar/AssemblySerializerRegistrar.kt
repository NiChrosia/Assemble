package assemble.common.type.api.registrar

import assemble.common.type.api.assembly.serializer.SlotSerializerType
import net.minecraft.util.Identifier
import nucleus.common.builtin.division.ModRoot
import nucleus.common.registrar.Registrar

open class AssemblySerializerRegistrar<R : ModRoot<R>>(root: R) : Registrar<Identifier, SlotSerializerType<*>, R>(root)