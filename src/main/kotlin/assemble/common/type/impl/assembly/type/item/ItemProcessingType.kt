package assemble.common.type.impl.assembly.type.item

import assemble.common.Assemble
import assemble.common.type.api.assembly.serializer.SlotSerializer
import assemble.common.type.api.assembly.type.AssemblyType
import assemble.common.type.dsl.io.item.*
import assemble.common.type.impl.assembly.item.ItemProcessingAssembly
import com.google.gson.JsonObject
import net.minecraft.inventory.Inventory
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier

open class ItemProcessingType<C : Inventory> @JvmOverloads constructor(
    id: Identifier,
    val slots: List<Int> = listOf(0, 1)
) : AssemblyType<C, ItemProcessingAssembly<C>>(id) {
    val ingredient = SlotSerializer(Assemble.content.slotSerializer.ingredientStack, "ingredient")
    val result = SlotSerializer(Assemble.content.slotSerializer.itemStack, "result")

    override fun read(id: Identifier, json: JsonObject): ItemProcessingAssembly<C> {
        return ItemProcessingAssembly(id, ingredient(json), result(json), slots)
    }

    override fun pack(buffer: PacketByteBuf, assembly: ItemProcessingAssembly<C>) {
        ingredient(buffer, assembly.ingredient)
        result(buffer, assembly.result)
    }

    override fun unpack(id: Identifier, buffer: PacketByteBuf): ItemProcessingAssembly<C> {
        return ItemProcessingAssembly(id, ingredient(buffer), result(buffer), slots)
    }
}