package assemble.common.type.api.storage

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage

interface ItemStorageInventory {
    val itemStorage: CombinedStorage<ItemVariant, out SingleSlotStorage<ItemVariant>>
}