package assemble.common.type.impl.assembly.type.fluid

import assemble.common.type.api.assembly.type.AssemblyType
import assemble.common.type.dsl.io.fluid.*
import assemble.common.type.impl.assembly.fluid.FluidMixingAssembly
import assemble.common.type.api.storage.fluid.FluidInventory
import com.google.gson.JsonObject
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier

open class FluidMixingType<C : FluidInventory> @JvmOverloads constructor(
    id: Identifier,
    val slots: List<Int> = listOf(0, 1, 2)
) : AssemblyType<C, FluidMixingAssembly<C>>(id) {
    override fun deserialize(id: Identifier, json: JsonObject): FluidMixingAssembly<C> {
        val first = json["first"].asJsonObject.asFluidStack
        val second = json["second"].asJsonObject.asFluidStack
        val result = json["result"].asJsonObject.asFluidStack

        return FluidMixingAssembly(id, first, second, result, slots)
    }

    override fun pack(packet: PacketByteBuf, assembly: FluidMixingAssembly<C>) {
        packet.writeFluidStack(assembly.first)
        packet.writeFluidStack(assembly.second)
        packet.writeFluidStack(assembly.result)
    }

    override fun unpack(id: Identifier, packet: PacketByteBuf): FluidMixingAssembly<C> {
        val first = packet.readFluidStack()
        val second = packet.readFluidStack()
        val result = packet.readFluidStack()

        return FluidMixingAssembly(id, first, second, result, slots)
    }
}