package assemble.common.type.impl.assembly.type.item

import assemble.common.Assemble
import assemble.common.type.api.assembly.serializer.SlotSerializer
import assemble.common.type.api.assembly.type.AssemblyType
import assemble.common.type.impl.assembly.item.ItemMergingAssembly
import com.google.gson.JsonObject
import net.minecraft.inventory.Inventory
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier

open class ItemMergingType<C : Inventory> @JvmOverloads constructor(
    id: Identifier,
    val slots: List<Int> = listOf(0, 1, 2)
) : AssemblyType<C, ItemMergingAssembly<C>>(id) {
    val first = SlotSerializer(Assemble.content.slotSerializer.ingredientStack, "first")
    val second = SlotSerializer(Assemble.content.slotSerializer.ingredientStack, "second")
    val result = SlotSerializer(Assemble.content.slotSerializer.itemStack, "result")

    override fun read(id: Identifier, json: JsonObject): ItemMergingAssembly<C> {
        return ItemMergingAssembly(id, first(json), second(json), result(json), slots)
    }

    override fun pack(buffer: PacketByteBuf, assembly: ItemMergingAssembly<C>) {
        first(buffer, assembly.first)
        second(buffer, assembly.second)
        result(buffer, assembly.result)
    }

    override fun unpack(id: Identifier, buffer: PacketByteBuf): ItemMergingAssembly<C> {
        return ItemMergingAssembly(id, first(buffer), second(buffer), result(buffer), slots)
    }
}