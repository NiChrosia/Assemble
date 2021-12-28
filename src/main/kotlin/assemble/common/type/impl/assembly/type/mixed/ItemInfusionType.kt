package assemble.common.type.impl.assembly.type.mixed

import assemble.common.type.api.assembly.type.AssemblyType
import assemble.common.type.dsl.io.fluid.*
import assemble.common.type.dsl.io.item.asIngredient
import assemble.common.type.dsl.io.item.asItemStack
import assemble.common.type.dsl.io.item.readIngredient
import assemble.common.type.dsl.io.item.writeIngredient
import assemble.common.type.impl.assembly.mixed.ItemInfusionAssembly
import assemble.common.type.api.storage.fluid.FluidInventory
import com.google.gson.JsonObject
import net.minecraft.inventory.Inventory
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier

open class ItemInfusionType<C> @JvmOverloads constructor(
    id: Identifier,
    val slots: List<Int> = listOf(0, 1)
) : AssemblyType<C, ItemInfusionAssembly<C>>(id) where C : Inventory, C : FluidInventory {
    override fun deserialize(id: Identifier, json: JsonObject): ItemInfusionAssembly<C> {
        val item = json["item"].asJsonObject.asIngredient
        val (fluid, fluidAmount) = json["fluid"].asJsonObject.asFluidStack
        val result = json["result"].asJsonObject.asItemStack

        return ItemInfusionAssembly(id, item, fluid, fluidAmount, result, slots)
    }

    override fun pack(packet: PacketByteBuf, assembly: ItemInfusionAssembly<C>) {
        packet.writeIngredient(assembly.item)
        packet.writeFluidStack(assembly.fluid, assembly.fluidAmount)
        packet.writeItemStack(assembly.result)
    }

    override fun unpack(id: Identifier, packet: PacketByteBuf): ItemInfusionAssembly<C> {
        val item = packet.readIngredient()
        val (fluid, fluidAmount) = packet.readFluidStack()
        val result = packet.readItemStack()

        return ItemInfusionAssembly(id, item, fluid, fluidAmount, result, slots)
    }
}