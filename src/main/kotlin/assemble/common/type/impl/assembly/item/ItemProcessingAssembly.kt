package assemble.common.type.impl.assembly.item

import assemble.common.type.api.assembly.Assembly
import assemble.common.type.impl.assembly.slot.item.ItemInput
import assemble.common.type.impl.assembly.slot.item.ItemOutput
import assemble.common.type.impl.stack.IngredientStack
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.util.Identifier

open class ItemProcessingAssembly<C : Inventory> @JvmOverloads constructor(
    id: Identifier,
    val ingredient: IngredientStack,

    val result: ItemStack,
    val slots: List<Int> = listOf(0, 1)
) : Assembly<C>(
    id,
    listOf(ItemInput(slots[0], ingredient)),
    listOf(ItemOutput(slots[1], result))
)