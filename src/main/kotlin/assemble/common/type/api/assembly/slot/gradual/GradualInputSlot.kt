package assemble.common.type.api.assembly.slot.gradual

abstract class GradualInputSlot<C, T> : GradualSlot<C, T>() {
    abstract fun consume(container: C)
}