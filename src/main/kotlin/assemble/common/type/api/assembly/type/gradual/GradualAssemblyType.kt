package assemble.common.type.api.assembly.type.gradual

import assemble.common.type.api.assembly.GradualAssembly
import assemble.common.type.api.assembly.type.AssemblyType
import assemble.common.type.api.storage.Progressable
import net.minecraft.util.Identifier

abstract class GradualAssemblyType<C : Progressable, A : GradualAssembly<C>>(
    id: Identifier,
    /** Progress increment per tick. */
    val speed: Long,
    /** Progress threshold to craft. */
    val end: Long,
    /** Energy consumption per tick. */
    val consumption: Long
) : AssemblyType<C, A>(id)