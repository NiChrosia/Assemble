package assemble.common.type.impl.assembly.type.item

import assemble.common.type.api.assembly.type.AssemblyType
import assemble.common.type.dsl.io.item.*
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
        val first = json["first"].asJsonObject.asIngredientStack
        val second = json["second"].asJsonObject.asIngredientStack
        val result = json["result"].asJsonObject.asItemStack

        return ItemMergingAssembly(id, first, second, result, slots)
    }

    override fun pack(packet: PacketByteBuf, assembly: ItemMergingAssembly<C>) {
        packet.writeIngredientStack(assembly.first)
        packet.writeIngredientStack(assembly.second)
        packet.writeItemStack(assembly.result)
    }

    override fun unpack(id: Identifier, packet: PacketByteBuf): ItemMergingAssembly<C> {
        val first = packet.readIngredientStack()
        val second = packet.readIngredientStack()
        val result = packet.readItemStack()

        return ItemMergingAssembly(id, first, second, result, slots)
    }
}