package assemble.common.type.impl.assembly.fluid.slot

import assemble.common.type.api.assembly.slot.OutputSlot
import assemble.common.type.impl.storage.fluid.FluidInventory
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant

open class FluidOutputSlot<C : FluidInventory>(val slot: Int, val result: FluidVariant, val amount: Long) : OutputSlot<C, FluidVariant>() {
    override fun matches(container: C): Boolean {
        val slotFluid = container.getFluid(slot)
        val slotAmount = container.getAmount(slot)
        val slotCapacity = container.getCapacity(slot)

        val correctType = slotFluid == result || slotFluid.isBlank
        val notFull = slotAmount <= slotCapacity - amount

        return correctType && notFull
    }

    override fun craft(container: C) {
        val slotAmount = container.getAmount(slot)

        container.setAmount(slot, slotAmount + amount)
    }
}