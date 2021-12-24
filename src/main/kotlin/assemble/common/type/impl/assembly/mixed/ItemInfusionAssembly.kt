package assemble.common.type.impl.assembly.mixed

import assemble.common.type.api.assembly.Assembly
import assemble.common.type.impl.assembly.fluid.slot.FluidInputSlot
import assemble.common.type.impl.assembly.item.slot.ItemInputSlot
import assemble.common.type.impl.assembly.item.slot.ItemOutputSlot
import assemble.common.type.api.storage.fluid.FluidInventory
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

    val slots: List<Int> = listOf(0, 1, 2)
) : Assembly<C>(
    id,
    listOf(
        ItemInputSlot(slots[0], item),
        FluidInputSlot(slots[1], fluid, fluidAmount)
    ),
    listOf(
        ItemOutputSlot(slots[2], result)
    )
) where C : Inventory, C : FluidInventory