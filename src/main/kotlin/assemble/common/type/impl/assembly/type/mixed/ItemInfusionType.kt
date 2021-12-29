package assemble.common.type.impl.assembly.type.mixed

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
    override fun deserialize(id: Identifier, json: JsonObject): ItemInfusionAssembly<C> {
        val item = json["item"].asJsonObject.asIngredientStack
        val fluid = json["fluid"].asJsonObject.asFluidStack
        val result = json["result"].asJsonObject.asItemStack

        return ItemInfusionAssembly(id, item, fluid, result, slots)
    }

    override fun pack(packet: PacketByteBuf, assembly: ItemInfusionAssembly<C>) {
        packet.writeIngredientStack(assembly.item)
        packet.writeFluidStack(assembly.fluid)
        packet.writeItemStack(assembly.result)
    }

    override fun unpack(id: Identifier, packet: PacketByteBuf): ItemInfusionAssembly<C> {
        val item = packet.readIngredientStack()
        val fluid = packet.readFluidStack()
        val result = packet.readItemStack()

        return ItemInfusionAssembly(id, item, fluid, result, slots)
    }
}