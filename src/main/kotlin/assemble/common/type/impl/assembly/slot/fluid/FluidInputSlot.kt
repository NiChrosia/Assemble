package assemble.common.type.impl.assembly.slot.fluid

import assemble.common.type.api.assembly.slot.InputSlot
import assemble.common.type.api.storage.fluid.MultiFluidInventory
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction

open class FluidInputSlot<C : MultiFluidInventory>(val slot: Int, val type: FluidVariant, val amount: Long) : InputSlot<C, FluidVariant>() {
    override fun matches(container: C): Boolean {
        val part = container.fluidStorage.parts[slot]

        val correctType = part.resource == type || part.resource.isBlank
        val enoughFluid = part.amount >= amount

        return correctType && enoughFluid
    }

    override fun craft(container: C) {
        val part = container.fluidStorage.parts[slot]
        val transaction = Transaction.openOuter()

        part.extract(type, amount, transaction)

        transaction.commit()
    }
}