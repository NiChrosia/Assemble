package assemble.common.type.impl.assembly.slot.fluid

import assemble.common.type.api.assembly.slot.InputSlot
import assemble.common.type.api.storage.fluid.FluidInventory
import assemble.common.type.impl.stack.FluidStack
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction

open class FluidInput<C : FluidInventory>(val slot: Int, val stack: FluidStack) : InputSlot<C, FluidVariant>() {
    override fun matches(container: C): Boolean {
        val part = container.fluidStorage.parts[slot]

        val correctType = part.resource == stack.type || part.resource.isBlank
        val enoughFluid = part.amount >= stack.amount
        val supportsExtraction = part.supportsExtraction()

        return correctType && enoughFluid && supportsExtraction
    }

    override fun consume(container: C) {
        val part = container.fluidStorage.parts[slot]
        val transaction = Transaction.openOuter()

        part.extract(stack.type, stack.amount, transaction).also {
            checkExtraction(it, stack.amount)
        }

        transaction.commit()
    }
}