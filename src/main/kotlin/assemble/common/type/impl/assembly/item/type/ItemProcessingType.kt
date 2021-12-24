package assemble.common.type.impl.assembly.item.type

import assemble.common.type.api.assembly.type.AssemblyType
import assemble.common.type.dsl.io.item.asIngredient
import assemble.common.type.dsl.io.item.asItemStack
import assemble.common.type.dsl.io.item.readIngredient
import assemble.common.type.dsl.io.item.writeIngredient
import assemble.common.type.impl.assembly.item.ItemProcessingAssembly
import com.google.gson.JsonObject
import net.minecraft.inventory.Inventory
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier

open class ItemProcessingType<C : Inventory> @JvmOverloads constructor(
    id: Identifier,
    val slots: List<Int> = listOf(0, 1)
) : AssemblyType<C, ItemProcessingAssembly<C>>(id) {
    override fun deserialize(id: Identifier, json: JsonObject): ItemProcessingAssembly<C> {
        val ingredient = json["ingredient"].asJsonObject.asIngredient
        val result = json["result"].asJsonObject.asItemStack

        return ItemProcessingAssembly(id, ingredient, result, slots)
    }

    override fun pack(packet: PacketByteBuf, assembly: ItemProcessingAssembly<C>) {
        packet.writeIngredient(assembly.ingredient)
        packet.writeItemStack(assembly.result)
    }

    override fun unpack(id: Identifier, packet: PacketByteBuf): ItemProcessingAssembly<C> {
        val ingredient = packet.readIngredient()
        val result = packet.readItemStack()

        return ItemProcessingAssembly(id, ingredient, result, slots)
    }
}