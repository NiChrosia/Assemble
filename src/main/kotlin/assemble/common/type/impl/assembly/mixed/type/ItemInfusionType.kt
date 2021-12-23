package assemble.common.type.impl.assembly.mixed.type

import assemble.common.type.api.assembly.type.AssemblyType
import assemble.common.type.dsl.io.fluid.asFluid
import assemble.common.type.dsl.io.fluid.readFluid
import assemble.common.type.dsl.io.fluid.writeFluid
import assemble.common.type.dsl.io.item.asIngredient
import assemble.common.type.dsl.io.item.asItemStack
import assemble.common.type.dsl.io.item.readIngredient
import assemble.common.type.dsl.io.item.writeIngredient
import assemble.common.type.impl.assembly.mixed.ItemInfusionAssembly
import assemble.common.type.impl.storage.fluid.FluidInventory
import com.google.gson.JsonObject
import net.minecraft.inventory.Inventory
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier

open class ItemInfusionType<C>(id: Identifier) : AssemblyType<C, ItemInfusionAssembly<C>>(id) where C : Inventory, C : FluidInventory {
    override fun deserialize(id: Identifier, json: JsonObject): ItemInfusionAssembly<C> {
        val item = json["item"].asIngredient
        val (fluid, fluidAmount) = json["fluid"].asFluid
        val result = json["result"].asItemStack

        return ItemInfusionAssembly(id, item, fluid, fluidAmount, result)
    }

    override fun pack(packet: PacketByteBuf, assembly: ItemInfusionAssembly<C>) {
        packet.writeIngredient(assembly.item)
        packet.writeFluid(assembly.fluid, assembly.fluidAmount)
        packet.writeItemStack(assembly.result)
    }

    override fun unpack(id: Identifier, packet: PacketByteBuf): ItemInfusionAssembly<C> {
        val item = packet.readIngredient()
        val (fluid, fluidAmount) = packet.readFluid()
        val result = packet.readItemStack()

        return ItemInfusionAssembly(id, item, fluid, fluidAmount, result)
    }
}