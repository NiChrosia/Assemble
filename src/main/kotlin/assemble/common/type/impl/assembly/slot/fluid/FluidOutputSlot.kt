package assemble.common.type.impl.assembly.slot.fluid

import assemble.common.type.api.assembly.slot.OutputSlot
import assemble.common.type.api.storage.fluid.MultiFluidInventory
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction

open class FluidOutputSlot<C : MultiFluidInventory>(val slot: Int, val result: FluidVariant, val amount: Long) : OutputSlot<C, FluidVariant>() {
    override fun matches(container: C): Boolean {
        val part = container.fluidStorage.parts[slot]

        val correctType = part.resource == result || part.resource.isBlank
        val notFull = part.amount <= part.capacity - amount

        return correctType && notFull
    }

    override fun craft(container: C) {
        val part = container.fluidStorage.parts[slot]
        val transaction = Transaction.openOuter()

        part.insert(result, amount, transaction)

        transaction.commit()
    }
}