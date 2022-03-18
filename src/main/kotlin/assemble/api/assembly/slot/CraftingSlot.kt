package assemble.api.assembly.slot

abstract class CraftingSlot<R, in I> : AssemblySlot<R, I>() {
    abstract fun spaceAvailable(inventory: I): Boolean
    abstract fun craft(inventory: I)

    abstract class Type<R, in I, S : CraftingSlot<R, I>> : AssemblySlot.Type<R, I, S>()
}