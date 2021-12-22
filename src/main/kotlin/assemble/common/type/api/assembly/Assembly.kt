package assemble.common.type.api.assembly

import assemble.common.type.api.assembly.slot.InputSlot
import assemble.common.type.api.assembly.slot.OutputSlot
import net.minecraft.util.Identifier

open class Assembly<C>(val id: Identifier, val inputs: List<InputSlot<C, *>>, val outputs: List<OutputSlot<C, *>>)