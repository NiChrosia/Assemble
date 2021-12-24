package assemble.common.type.impl.assembly.item

import assemble.common.type.api.assembly.Assembly
import assemble.common.type.impl.assembly.item.slot.ItemInputSlot
import assemble.common.type.impl.assembly.item.slot.ItemOutputSlot
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.util.Identifier

open class ItemProcessingAssembly<C : Inventory> @JvmOverloads constructor(
    id: Identifier,
    val ingredient: Ingredient,
    val result: ItemStack,
    val slots: List<Int> = listOf(0, 1)
) : Assembly<C>(
    id,
    listOf(ItemInputSlot(slots[0], ingredient)),
    listOf(ItemOutputSlot(slots[1], result))
)