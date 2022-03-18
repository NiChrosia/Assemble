package assemble.impl.assembly.adapter

import net.minecraft.item.ItemStack

// TODO somehow add Inventory compatibility
interface ItemAdapter {
    fun getStack(slot: Int): ItemStack
    fun setStack(slot: Int, stack: ItemStack)
}