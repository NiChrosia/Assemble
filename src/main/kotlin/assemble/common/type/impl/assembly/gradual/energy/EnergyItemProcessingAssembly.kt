package assemble.common.type.impl.assembly.gradual.energy

import assemble.common.type.api.assembly.GradualAssembly
import assemble.common.type.api.storage.EnergyStorageInventory
import assemble.common.type.api.storage.Progressable
import assemble.common.type.impl.assembly.slot.gradual.GradualEnergyInput
import assemble.common.type.impl.assembly.slot.item.ItemInput
import assemble.common.type.impl.assembly.slot.item.ItemOutput
import assemble.common.type.impl.stack.IngredientStack
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier

open class EnergyItemProcessingAssembly<C> @JvmOverloads constructor(
    id: Identifier,
    val ingredient: IngredientStack,

    val result: ItemStack,
    val slots: List<Int> = listOf(0, 1),
    val consumption: Long,
    speed: Long,
    end: Long,
) : GradualAssembly<C>(
    id,
    listOf(ItemInput(slots[0], ingredient)),
    listOf(ItemOutput(slots[1], result)),

    listOf(GradualEnergyInput(consumption)),
    listOf(),
    speed,
    end,
) where C : Inventory, C : EnergyStorageInventory, C : Progressable