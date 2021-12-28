package assemble.common.type.impl.assembly.gradual.energy

import assemble.common.type.api.assembly.GradualAssembly
import assemble.common.type.api.assembly.slot.InputSlot
import assemble.common.type.api.assembly.slot.OutputSlot
import assemble.common.type.api.storage.EnergyInventory
import assemble.common.type.api.storage.ProgressInventory
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.util.Identifier

abstract class EnergyGradualAssembly<C>(
    id: Identifier,
    inputs: List<InputSlot<C, *>>,
    outputs: List<OutputSlot<C, *>>,
    speed: Long,
    end: Long,
    /** Energy consumption per tick. */
    val consumption: Long
) : GradualAssembly<C>(id, inputs, outputs, speed, end) where C : EnergyInventory, C : ProgressInventory {
    override fun matches(container: C): Boolean {
        return super.matches(container).let {
            val aboveZero = container.energyStorage.amount > 0L

            aboveZero && it
        }
    }

    override fun consume(container: C) {
        val storage = container.energyStorage
        val transaction = Transaction.openOuter()

        storage.extract(consumption, transaction)

        transaction.commit()
    }
}