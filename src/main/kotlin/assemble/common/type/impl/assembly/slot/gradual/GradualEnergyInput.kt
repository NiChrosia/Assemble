package assemble.common.type.impl.assembly.slot.gradual

import assemble.common.type.api.assembly.slot.gradual.GradualInputSlot
import assemble.common.type.api.storage.EnergyInventory
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction

open class GradualEnergyInput<C : EnergyInventory>(val amount: Long) : GradualInputSlot<C, Long>() {
    override fun matches(container: C): Boolean {
        val storage = container.energyStorage

        val supportsExtraction = storage.supportsExtraction()
        val enoughEnergy = storage.amount >= amount

        return supportsExtraction && enoughEnergy
    }

    override fun consume(container: C) {
        val transaction = Transaction.openOuter()

        container.energyStorage.extract(amount, transaction).also {
            checkExtraction(it, amount)
        }

        transaction.commit()
    }
}