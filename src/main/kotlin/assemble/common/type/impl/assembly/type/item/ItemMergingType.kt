package assemble.common.type.impl.assembly.type.item

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

open class ItemMergingType<C : Inventory> @JvmOverloads constructor(
    id: Identifier,
    val slots: List<Int> = listOf(0, 1, 2)
) : AssemblyType<C, ItemMergingAssembly<C>>(id) {
    override fun deserialize(id: Identifier, json: JsonObject): ItemMergingAssembly<C> {
        val first = json["first"].asJsonObject.asIngredient
        val second = json["second"].asJsonObject.asIngredient
        val result = json["result"].asJsonObject.asItemStack

        return ItemMergingAssembly(id, first, second, result, slots)
    }

    override fun pack(packet: PacketByteBuf, assembly: ItemMergingAssembly<C>) {
        packet.writeIngredient(assembly.first)
        packet.writeIngredient(assembly.second)
        packet.writeItemStack(assembly.result)
    }

    override fun unpack(id: Identifier, packet: PacketByteBuf): ItemMergingAssembly<C> {
        val first = packet.readIngredient()
        val second = packet.readIngredient()
        val result = packet.readItemStack()

        return ItemMergingAssembly(id, first, second, result, slots)
    }
}