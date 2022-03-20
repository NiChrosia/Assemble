package assemble.api.assembly.slot

abstract class ResultSlot<R, I> : AssemblySlot<R, I>() {
    abstract fun spaceAvailable(inventory: I): Boolean
    abstract fun craft(inventory: I)

    abstract class Type<R, I, S : ResultSlot<R, I>> : AssemblySlot.Type<R, I, S>()
}