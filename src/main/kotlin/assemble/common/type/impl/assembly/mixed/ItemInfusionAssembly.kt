package assemble.common.type.impl.assembly.mixed

import assemble.common.type.api.assembly.Assembly
import assemble.common.type.impl.assembly.slot.item.ItemInput
import assemble.common.type.impl.assembly.slot.item.ItemOutput
import assemble.common.type.api.storage.fluid.SingleFluidInventory
import assemble.common.type.impl.assembly.slot.fluid.SingleFluidInput
import assemble.common.type.impl.stack.FluidStack
import assemble.common.type.impl.stack.IngredientStack
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.util.Identifier

open class ItemInfusionAssembly<C> @JvmOverloads constructor(
    id: Identifier,
    val item: IngredientStack,
    val fluid: FluidStack,
    val result: ItemStack,
    val slots: List<Int> = listOf(0, 1)
) : Assembly<C>(
    id,
    listOf(
        ItemInput(slots[0], item),
        SingleFluidInput(fluid)
    ),
    listOf(
        ItemOutput(slots[1], result)
    )
) where C : Inventory, C : SingleFluidInventory