package assemble.common.type.api.assembly.slot

/** The abstract core of the consumption/production assembly system. */
abstract class AssemblySlot<C, T> {
    /** Whether this slot matches the given [container]. Used to check for valid types, full slots, enough content, and various other things. */
    abstract fun matches(container: C): Boolean

    open fun checkInsertion(amount: Long, intended: Long) {
        if (amount < intended) throw IllegalStateException("Inserted less than expected despite matching container.")
    }

    open fun checkExtraction(amount: Long, intended: Long) {
        if (amount < intended) throw IllegalStateException("Extracted less than expected despite matching container.")
    }
}