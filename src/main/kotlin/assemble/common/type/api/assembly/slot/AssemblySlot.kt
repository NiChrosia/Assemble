package assemble.common.type.api.assembly.slot

abstract class AssemblySlot<C, T> {
    abstract fun matches(container: C): Boolean
    abstract fun craft(container: C)
}