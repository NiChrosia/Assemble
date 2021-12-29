package assemble.common.type.api.assembly.slot.gradual

abstract class GradualOutputSlot<C, T> : GradualSlot<C, T>() {
    abstract fun produce(container: C)
}