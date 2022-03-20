package assemble.impl.assembly.adapter.power

import assemble.impl.assembly.adapter.Adapters

interface PowerAdapter {
    fun getPower(): Long
    fun getPowerCapacity(): Long

    fun setPower(power: Long)

    fun addPower(amount: Long) {
        Adapters.add(this::getPower, this::setPower, amount)
    }

    fun subtractPower(amount: Long) {
        Adapters.subtract(this::getPower, this::setPower, amount)
    }
}