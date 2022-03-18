package assemble.impl.assembly.resource

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.tag.Tag

class ItemIngredient(val items: List<Item> = listOf(), val nbt: NbtCompound = NbtCompound()) {
    fun matches(item: Item): Boolean {
        return items.any { item == it }
    }

    fun matches(nbt: NbtCompound?): Boolean {
        return nbt?.equals(nbt) ?: false
    }

    fun matches(stack: ItemStack): Boolean {
        return matches(stack.item) && matches(stack.nbt)
    }

    constructor(tag: Tag<Item>, nbt: NbtCompound = NbtCompound()) : this(tag.values(), nbt)
}