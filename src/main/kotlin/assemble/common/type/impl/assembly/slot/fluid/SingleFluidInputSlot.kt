package assemble.common.type.impl.assembly.slot.fluid

import assemble.common.type.api.assembly.slot.InputSlot
import assemble.common.type.api.storage.fluid.FluidInventory
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction

/** An alternative to [FluidInputSlot] for usage when only one storage is present. */
open class SingleFluidInputSlot<C : FluidInventory>(val type: FluidVariant, val amount: Long) : InputSlot<C, FluidVariant>() {
    override fun matches(container: C): Boolean {
        val storage = container.fluidStorage

        val correctType = storage.resource == type || storage.resource.isBlank
        val enoughFluid = storage.amount >= amount

        return correctType && enoughFluid
    }

    override fun craft(container: C) {
        val storage = container.fluidStorage
        val transaction = Transaction.openOuter()

        storage.extract(type, amount, transaction)

        transaction.commit()
    }
}