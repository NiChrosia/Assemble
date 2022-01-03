package assemble.common.type.impl.assembly.type.mixed

import assemble.common.Assemble
import assemble.common.type.api.assembly.serializer.SlotSerializer
import assemble.common.type.api.assembly.type.AssemblyType
import assemble.common.type.dsl.io.fluid.*
import assemble.common.type.impl.assembly.mixed.ItemInfusionAssembly
import assemble.common.type.api.storage.fluid.SingleFluidInventory
import assemble.common.type.dsl.io.item.*
import com.google.gson.JsonObject
import net.minecraft.inventory.Inventory
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier

open class ItemInfusionType<C> @JvmOverloads constructor(
    id: Identifier,
    val slots: List<Int> = listOf(0, 1)
) : AssemblyType<C, ItemInfusionAssembly<C>>(id) where C : Inventory, C : SingleFluidInventory {
    val item = SlotSerializer(Assemble.content.slotSerializer.ingredientStack, "item")
    val fluid = SlotSerializer(Assemble.content.slotSerializer.fluidStack, "fluid")
    val result = SlotSerializer(Assemble.content.slotSerializer.itemStack, "result")

    override fun read(id: Identifier, json: JsonObject): ItemInfusionAssembly<C> {
        return ItemInfusionAssembly(id, item(json), fluid(json), result(json), slots)
    }

    override fun pack(buffer: PacketByteBuf, assembly: ItemInfusionAssembly<C>) {
        item(buffer, assembly.item)
        fluid(buffer, assembly.fluid)
        result(buffer, assembly.result)
    }

    override fun unpack(id: Identifier, buffer: PacketByteBuf): ItemInfusionAssembly<C> {
        return ItemInfusionAssembly(id, item(buffer), fluid(buffer), result(buffer), slots)
    }
}