package assemble.impl.assembly.adapter.item

import assemble.impl.assembly.adapter.Adapters
import net.minecraft.item.Item
import net.minecraft.nbt.NbtCompound

interface ItemAdapter {
    fun getItem(slot: Int): Item
    fun getNbt(slot: Int): NbtCompound?
    fun getCount(slot: Int): Int

    fun getItemCapacity(slot: Int): Int {
        return Item.DEFAULT_MAX_COUNT
    }

    fun setItem(slot: Int, item: Item)
    fun setCount(slot: Int, count: Int)

    fun addItems(slot: Int, count: Int) {
        Adapters.add(this::getCount, this::setCount, slot, count)
    }

    fun subtractItems(slot: Int, count: Int) {
        Adapters.subtract(this::getCount, this::setCount, slot, count)
    }
}