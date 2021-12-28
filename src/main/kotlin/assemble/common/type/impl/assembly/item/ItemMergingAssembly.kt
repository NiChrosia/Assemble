package assemble.common.type.impl.assembly.item

import assemble.common.type.api.assembly.Assembly
import assemble.common.type.impl.assembly.slot.item.ItemInputSlot
import assemble.common.type.impl.assembly.slot.item.ItemOutputSlot
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.util.Identifier

open class ItemMergingAssembly<C : Inventory> @JvmOverloads constructor(
    id: Identifier,
    val first: Ingredient,
    val second: Ingredient,
    val result: ItemStack,
    val slots: List<Int> = listOf(0, 1, 2)
) : Assembly<C>(
    id,
    listOf(
        ItemInputSlot(slots[0], first),
        ItemInputSlot(slots[1], second)
    ),
    listOf(
        ItemOutputSlot(slots[2], result)
    )
)