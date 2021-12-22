package assemble.common.type.impl.assembly.item.type

import assemble.common.type.api.assembly.type.AssemblyType
import assemble.common.type.dsl.io.item.asIngredient
import assemble.common.type.dsl.io.item.asItemStack
import assemble.common.type.dsl.io.item.readIngredient
import assemble.common.type.dsl.io.item.writeIngredient
import assemble.common.type.impl.assembly.item.ItemMergingAssembly
import com.google.gson.JsonObject
import net.minecraft.inventory.Inventory
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier

open class ItemMergingType<C : Inventory>(id: Identifier) : AssemblyType<C, ItemMergingAssembly<C>>(id) {
    override fun deserialize(id: Identifier, json: JsonObject): ItemMergingAssembly<C> {
        val first = json["first"].asIngredient
        val second = json["second"].asIngredient
        val result = json["result"].asItemStack
        val slots = json["slots"].asJsonArray.map { it.asInt }

        return ItemMergingAssembly(id, first, second, result, slots)
    }

    override fun pack(packet: PacketByteBuf, assembly: ItemMergingAssembly<C>) {
        packet.writeIngredient(assembly.first)
        packet.writeIngredient(assembly.second)
        packet.writeItemStack(assembly.result)
        packet.writeIntArray(assembly.slots.toIntArray())
    }

    override fun unpack(id: Identifier, packet: PacketByteBuf): ItemMergingAssembly<C> {
        val first = packet.readIngredient()
        val second = packet.readIngredient()
        val result = packet.readItemStack()
        val slots = packet.readIntArray().toList()

        return ItemMergingAssembly(id, first, second, result, slots)
    }
}