package assemble.common.type.impl.assembly.slot.fluid

import assemble.common.type.api.assembly.slot.OutputSlot
import assemble.common.type.api.storage.fluid.FluidInventory
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction

/** An alternative to [FluidOutputSlot] for usage when only one storage is present. */
open class SingleFluidOutputSlot<C : FluidInventory>(val slot: Int, val result: FluidVariant, val amount: Long) : OutputSlot<C, FluidVariant>() {
    override fun matches(container: C): Boolean {
        val storage = container.fluidStorage

        val correctType = storage.resource == result || storage.resource.isBlank
        val notFull = storage.amount <= storage.capacity - amount

        return correctType && notFull
    }

    override fun craft(container: C) {
        val storage = container.fluidStorage
        val transaction = Transaction.openOuter()

        storage.insert(result, amount, transaction)

        transaction.commit()
    }
}