package assemble.api.assembly

import assemble.api.assembly.slot.IngredientSlot
import assemble.api.assembly.slot.ResultSlot
import assemble.api.assembly.slot.incremental.ConsumptionSlot
import assemble.api.assembly.slot.incremental.GenerationSlot
import assemble.impl.assembly.adapter.progress.ProgressAdapter

open class IncrementalAssembly<I : ProgressAdapter>(
    ingredients: List<IngredientSlot<*, I>>,
    results: List<ResultSlot<*, I>>,
    val consumption: List<ConsumptionSlot<*, I>>,
    val generation: List<GenerationSlot<*, I>>
) : Assembly<I>(ingredients, results) {
    override fun matches(inventory: I): Boolean {
        return super.matches(inventory) && consumption.all { it.enoughInput(inventory) } && generation.all { it.spaceAvailable(inventory) }
    }

    open fun increment(inventory: I) {
        for (slot in consumption) slot.consume(inventory)
        for (slot in generation) slot.generate(inventory)

        val increment = inventory.getIncrement()
        val progress = inventory.getProgress()

        inventory.setProgress(progress + increment)
    }

    open fun checkProgress(inventory: I) {
        val progress = inventory.getProgress()
        val maxProgress = inventory.getMaxProgress()

        if (progress >= maxProgress) {
            craft(inventory)

            inventory.setProgress(0)
        }
    }
}