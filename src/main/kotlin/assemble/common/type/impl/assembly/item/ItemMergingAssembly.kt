package assemble.common.type.impl.assembly.item

import assemble.common.type.api.assembly.Assembly
import assemble.common.type.impl.assembly.slot.item.ItemInput
import assemble.common.type.impl.assembly.slot.item.ItemOutput
import assemble.common.type.impl.stack.IngredientStack
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.util.Identifier

open class ItemMergingAssembly<C : Inventory> @JvmOverloads constructor(
    id: Identifier,
    val first: IngredientStack,

    val second: IngredientStack,

    val result: ItemStack,
    val slots: List<Int> = listOf(0, 1, 2)
) : Assembly<C>(
    id,
    listOf(
        ItemInput(slots[0], first),
        ItemInput(slots[1], second)
    ),
    listOf(
        ItemOutput(slots[2], result)
    )
)