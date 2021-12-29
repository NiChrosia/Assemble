package assemble.common.type.impl.assembly.slot.fluid

import assemble.common.type.api.assembly.slot.InputSlot
import assemble.common.type.api.storage.fluid.SingleFluidInventory
import assemble.common.type.impl.stack.FluidStack
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction

/** An alternative to [FluidInput] for usage when only one storage is present. */
open class SingleFluidInput<C : SingleFluidInventory>(val stack: FluidStack) : InputSlot<C, FluidVariant>() {
    override fun matches(container: C): Boolean {
        val storage = container.fluidStorage

        val correctType = storage.resource == stack.type || storage.resource.isBlank
        val enoughFluid = storage.amount >= stack.amount
        val supportsExtraction = storage.supportsExtraction()

        return correctType && enoughFluid && supportsExtraction
    }

    override fun consume(container: C) {
        val storage = container.fluidStorage
        val transaction = Transaction.openOuter()

        storage.extract(stack.type, stack.amount, transaction).also {
            checkExtraction(it, stack.amount)
        }

        transaction.commit()
    }
}