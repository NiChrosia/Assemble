package assemble.impl.assembly.adapter.item

import net.minecraft.item.Item
import net.minecraft.nbt.NbtCompound

interface ItemAdapter {
    fun getItem(slot: Int): Item
    fun getNbt(slot: Int): NbtCompound?
    fun getCount(slot: Int): Int
    fun getMaxCount(slot: Int): Int

    fun setItem(slot: Int, item: Item)
    fun setCount(slot: Int, count: Int)
}