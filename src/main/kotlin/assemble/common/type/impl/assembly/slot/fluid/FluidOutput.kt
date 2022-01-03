package assemble.common.type.impl.assembly.slot.fluid

import assemble.common.type.api.assembly.slot.OutputSlot
import assemble.common.type.api.storage.fluid.FluidStorageInventory
import assemble.common.type.impl.stack.FluidStack
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction

open class FluidOutput<C : FluidStorageInventory>(val slot: Int, val result: FluidStack) : OutputSlot<C, FluidVariant>() {
    override fun matches(container: C): Boolean {
        val part = container.fluidStorage.parts[slot]

        val correctType = part.resource == result.type || part.resource.isBlank
        val enoughSpace = part.amount <= part.capacity - result.amount
        val supportsInsertion = part.supportsInsertion()

        return correctType && enoughSpace && supportsInsertion
    }

    override fun produce(container: C) {
        val part = container.fluidStorage.parts[slot]
        val transaction = Transaction.openOuter()

        part.insert(result.type, result.amount, transaction).also {
            checkInsertion(it, result.amount)
        }

        transaction.commit()
    }
}