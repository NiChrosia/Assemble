package assemble.impl.assembly.resource

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.tag.Tag
import net.minecraft.tag.TagKey

class ItemIngredient(val items: List<Item> = emptyList(), val nbt: NbtCompound = NbtCompound()) {
    fun matches(item: Item): Boolean {
        return items.any { item == it }
    }

    fun matches(nbt: NbtCompound?): Boolean {
        if ((nbt == null || nbt.isEmpty) && this.nbt.isEmpty) return true

        return this.nbt == nbt
    }

    fun matches(stack: ItemStack): Boolean {
        return matches(stack.item) && matches(stack.nbt)
    }
}