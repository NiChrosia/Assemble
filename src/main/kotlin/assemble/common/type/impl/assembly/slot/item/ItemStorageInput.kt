package assemble.common.type.impl.assembly.slot.item

import assemble.common.type.api.assembly.slot.InputSlot
import assemble.common.type.api.storage.ItemInventory
import assemble.common.type.impl.stack.IngredientStack
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.recipe.Ingredient

open class ItemStorageInput<C : ItemInventory>(val slot: Int, val stack: IngredientStack) : InputSlot<C, Ingredient>() {
    override fun matches(container: C): Boolean {
        val storage = container.itemStorage.parts[slot]

        val supportsExtraction = storage.supportsExtraction()
        val matches = stack.type.test(storage.resource.toStack())
        val enoughItems = storage.amount >= stack.amount

        return supportsExtraction && matches && enoughItems
    }

    override fun consume(container: C) {
        val storage = container.itemStorage.parts[slot]
        val transaction = Transaction.openOuter()

        storage.extract(storage.resource, stack.amount, transaction).also {
            checkExtraction(it, stack.amount)
        }

        transaction.commit()
    }
}