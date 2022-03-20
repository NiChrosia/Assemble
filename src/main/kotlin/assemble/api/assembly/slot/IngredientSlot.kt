package assemble.api.assembly.slot

abstract class IngredientSlot<R, I> : AssemblySlot<R, I>() {
    abstract fun enoughIngredients(inventory: I): Boolean
    abstract fun consume(inventory: I)

    abstract class Type<R, I, S : IngredientSlot<R, I>> : AssemblySlot.Type<R, I, S>()
}