package assemble.common.type.impl.assembly.energy

import assemble.common.type.api.storage.fluid.EnergyInventory
import assemble.common.type.api.storage.fluid.ProgressInventory
import assemble.common.type.impl.assembly.EnergyGradualAssembly
import assemble.common.type.impl.assembly.item.slot.ItemInputSlot
import assemble.common.type.impl.assembly.item.slot.ItemOutputSlot
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.util.Identifier

open class EnergyItemProcessingAssembly<C> @JvmOverloads constructor(
    id: Identifier,
    val ingredient: Ingredient,
    val result: ItemStack,
    val slots: List<Int> = listOf(0, 1),
    speed: Long,
    end: Long,
    consumption: Long
) : EnergyGradualAssembly<C>(
    id,
    listOf(ItemInputSlot(slots[0], ingredient)),
    listOf(ItemOutputSlot(slots[1], result)),
    speed,
    end,
    consumption
) where C : Inventory, C : EnergyInventory, C : ProgressInventory