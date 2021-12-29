package assemble.common.type.impl.assembly.slot.gradual

import assemble.common.type.api.assembly.slot.gradual.GradualOutputSlot
import assemble.common.type.api.storage.EnergyInventory
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction

open class GradualEnergyOutput<C : EnergyInventory>(val amount: Long) : GradualOutputSlot<C, Long>() {
    override fun matches(container: C): Boolean {
        val storage = container.energyStorage

        val supportsInsertion = storage.supportsInsertion()
        val notFull = storage.amount <= storage.capacity - amount

        return supportsInsertion && notFull
    }

    override fun produce(container: C) {
        val transaction = Transaction.openOuter()

        container.energyStorage.insert(amount, transaction).also {
            checkInsertion(it, amount)
        }

        transaction.commit()
    }
}