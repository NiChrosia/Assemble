package assemble.api.assembly.slot

abstract class CraftSlot<R, in I> : AssemblySlot<R, I>() {
    abstract fun spaceAvailable(inventory: I): Boolean
    abstract fun craft(inventory: I)

    abstract class Type<R, in I, S : CraftSlot<R, I>> : AssemblySlot.Type<R, I, S>()
}