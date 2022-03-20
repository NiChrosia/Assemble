package assemble.api.assembly.slot.incremental

import assemble.api.assembly.slot.AssemblySlot
import assemble.impl.assembly.adapter.progress.ProgressAdapter

abstract class ConsumptionSlot<R, I : ProgressAdapter> : AssemblySlot<R, I>() {
    abstract fun enoughInput(inventory: I): Boolean
    abstract fun consume(inventory: I)

    abstract class Type<R, I : ProgressAdapter, S : ConsumptionSlot<R, I>> : AssemblySlot.Type<R, I, S>()
}