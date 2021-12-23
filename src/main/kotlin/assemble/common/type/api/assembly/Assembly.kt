package assemble.common.type.api.assembly

import assemble.common.type.api.assembly.slot.InputSlot
import assemble.common.type.api.assembly.slot.OutputSlot
import assemble.common.type.api.assembly.type.AssemblyType
import net.minecraft.util.Identifier

abstract class Assembly<C>(val id: Identifier, val inputs: List<InputSlot<C, *>>, val outputs: List<OutputSlot<C, *>>)