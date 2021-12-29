package assemble.common.type.impl.assembly.slot.item

import assemble.common.type.api.assembly.slot.OutputSlot
import assemble.common.type.api.storage.ItemInventory
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.item.ItemStack

open class ItemStorageOutput<C : ItemInventory>(val slot: Int, val result: ItemStack) : OutputSlot<C, ItemStack>() {
    override fun matches(container: C): Boolean {
        val storage = container.itemStorage.parts[slot]

        val supportsInsertion = storage.supportsInsertion()
        val notFull = storage.amount < storage.capacity - result.count
        val correctType = storage.resource.item == result.item

        return supportsInsertion && notFull && correctType
    }

    override fun produce(container: C) {
        val storage = container.itemStorage.parts[slot]
        val transaction = Transaction.openOuter()

        storage.insert(ItemVariant.of(result), result.count.toLong(), transaction).also {
            checkInsertion(it, result.count.toLong())
        }

        transaction.commit()
    }
}