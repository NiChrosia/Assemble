package assemble.api.assembly.slot.incremental

import assemble.api.assembly.slot.AssemblySlot
import assemble.impl.assembly.adapter.progress.ProgressAdapter

abstract class GenerationSlot<R, I : ProgressAdapter> : AssemblySlot<R, I>() {
    abstract fun spaceAvailable(inventory: I): Boolean
    abstract fun generate(inventory: I)

    abstract class Type<R, I : ProgressAdapter, S : GenerationSlot<R, I>> : AssemblySlot.Type<R, I, S>()
}