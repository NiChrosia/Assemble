package assemble.common.type.api.assembly.slot

/** The abstract core of the consumption/production assembly system. */
abstract class AssemblySlot<C, T> {
    /** Whether this slot matches the given [container]. Used to check for valid types, full slots, enough content, and various other things. */
    abstract fun matches(container: C): Boolean

    /** Craft using the given [container]. Used to either consume ingredients or produce results. */
    abstract fun craft(container: C)
}