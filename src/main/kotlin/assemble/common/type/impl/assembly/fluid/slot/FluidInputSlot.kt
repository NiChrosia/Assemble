package assemble.common.type.impl.assembly.fluid.slot

import assemble.common.type.api.assembly.slot.InputSlot
import assemble.common.type.impl.storage.fluid.FluidInventory
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant

open class FluidInputSlot<C : FluidInventory>(val slot: Int, val type: FluidVariant, val amount: Long) : InputSlot<C, FluidVariant>() {
    override fun matches(container: C): Boolean {
        val slotFluid = container.getFluid(slot)
        val slotAmount = container.getAmount(slot)

        val correctType = slotFluid == type || slotFluid.isBlank
        val enoughFluid = slotAmount >= amount

        return correctType && enoughFluid
    }

    override fun craft(container: C) {
        val slotAmount = container.getAmount(slot)

        container.setAmount(slot, slotAmount - amount)
    }
}