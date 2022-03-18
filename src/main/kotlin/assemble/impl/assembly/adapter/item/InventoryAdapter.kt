package assemble.impl.assembly.adapter.item

import net.minecraft.inventory.Inventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound

interface InventoryAdapter : Inventory, ItemAdapter {
    override fun getItem(slot: Int): Item {
        return getStack(slot).item
    }

    override fun getNbt(slot: Int): NbtCompound? {
        return getStack(slot).nbt
    }

    override fun getCount(slot: Int): Int {
        return getStack(slot).count
    }

    override fun getMaxCount(slot: Int): Int {
        return getStack(slot).maxCount
    }

    override fun setItem(slot: Int, item: Item) {
        val count = getCount(slot)
        if (count == 0) return

        val stack = ItemStack(item, count)

        setStack(slot, stack)
    }

    override fun setCount(slot: Int, count: Int) {
        val stack = getStack(slot)
        stack.count = count
    }
}