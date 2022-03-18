package assemble.api.assembly.slot

abstract class ConsumeSlot<R, in I> : AssemblySlot<R, I>() {
    abstract fun enoughIngredients(inventory: I): Boolean
    abstract fun consume(inventory: I)

    abstract class Type<R, in I, S : ConsumeSlot<R, I>> : AssemblySlot.Type<R, I, S>()
}