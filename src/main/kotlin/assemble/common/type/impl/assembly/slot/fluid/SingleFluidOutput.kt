package assemble.common.type.impl.assembly.slot.fluid

import assemble.common.type.api.assembly.slot.OutputSlot
import assemble.common.type.api.storage.fluid.SingleFluidInventory
import assemble.common.type.impl.stack.FluidStack
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction

/** An alternative to [FluidOutput] for usage when only one storage is present. */
open class SingleFluidOutput<C : SingleFluidInventory>(val slot: Int, val result: FluidStack) : OutputSlot<C, FluidVariant>() {
    override fun matches(container: C): Boolean {
        val storage = container.fluidStorage

        val correctType = storage.resource == result.type || storage.resource.isBlank
        val notFull = storage.amount <= storage.capacity - result.amount
        val supportsInsertion = storage.supportsInsertion()

        return correctType && notFull && supportsInsertion
    }

    override fun produce(container: C) {
        val storage = container.fluidStorage
        val transaction = Transaction.openOuter()

        storage.insert(result.type, result.amount, transaction).also {
            checkInsertion(it, result.amount)
        }

        transaction.commit()
    }
}