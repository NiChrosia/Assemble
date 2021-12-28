package assemble.common.type.impl.assembly.mixed

import assemble.common.type.api.assembly.Assembly
import assemble.common.type.impl.assembly.slot.item.ItemInputSlot
import assemble.common.type.impl.assembly.slot.item.ItemOutputSlot
import assemble.common.type.api.storage.fluid.FluidInventory
import assemble.common.type.impl.assembly.slot.fluid.SingleFluidInputSlot
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.util.Identifier

open class ItemInfusionAssembly<C> @JvmOverloads constructor(
    id: Identifier,
    val item: Ingredient,

    val fluid: FluidVariant,
    val fluidAmount: Long,

    val result: ItemStack,

    val slots: List<Int> = listOf(0, 1)
) : Assembly<C>(
    id,
    listOf(
        ItemInputSlot(slots[0], item),
        SingleFluidInputSlot(fluid, fluidAmount)
    ),
    listOf(
        ItemOutputSlot(slots[1], result)
    )
) where C : Inventory, C : FluidInventory