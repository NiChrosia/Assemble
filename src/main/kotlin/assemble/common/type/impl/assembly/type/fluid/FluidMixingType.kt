package assemble.common.type.impl.assembly.type.fluid

import assemble.common.Assemble
import assemble.common.type.api.assembly.serializer.SlotSerializer
import assemble.common.type.api.assembly.type.AssemblyType
import assemble.common.type.dsl.io.fluid.*
import assemble.common.type.impl.assembly.fluid.FluidMixingAssembly
import assemble.common.type.api.storage.fluid.FluidStorageInventory
import com.google.gson.JsonObject
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier

open class FluidMixingType<C : FluidStorageInventory> @JvmOverloads constructor(
    id: Identifier,
    val slots: List<Int> = listOf(0, 1, 2)
) : AssemblyType<C, FluidMixingAssembly<C>>(id) {
    val first = SlotSerializer(Assemble.content.slotSerializer.fluidStack, "first")
    val second = SlotSerializer(Assemble.content.slotSerializer.fluidStack, "second")
    val result = SlotSerializer(Assemble.content.slotSerializer.fluidStack, "result")

    override fun read(id: Identifier, json: JsonObject): FluidMixingAssembly<C> {
        return FluidMixingAssembly(id, first(json), second(json), result(json), slots)
    }

    override fun pack(buffer: PacketByteBuf, assembly: FluidMixingAssembly<C>) {
        first(buffer, assembly.first)
        second(buffer, assembly.second)
        result(buffer, assembly.result)
    }

    override fun unpack(id: Identifier, buffer: PacketByteBuf): FluidMixingAssembly<C> {
        return FluidMixingAssembly(id, first(buffer), second(buffer), result(buffer), slots)
    }
}