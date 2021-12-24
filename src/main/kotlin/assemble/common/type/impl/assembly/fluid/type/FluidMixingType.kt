package assemble.common.type.impl.assembly.fluid.type

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
        val (first, firstAmount) = json["first"].asJsonObject.asFluidStack
        val (second, secondAmount) = json["second"].asJsonObject.asFluidStack
        val (result, resultAmount) = json["result"].asJsonObject.asFluidStack

        return FluidMixingAssembly(id, first, firstAmount, second, secondAmount, result, resultAmount, slots)
    }

    override fun pack(packet: PacketByteBuf, assembly: FluidMixingAssembly<C>) {
        packet.writeFluidStack(assembly.first, assembly.firstAmount)
        packet.writeFluidStack(assembly.second, assembly.secondAmount)
        packet.writeFluidStack(assembly.result, assembly.resultAmount)
    }

    override fun unpack(id: Identifier, packet: PacketByteBuf): FluidMixingAssembly<C> {
        val (first, firstAmount) = packet.readFluidStack()
        val (second, secondAmount) = packet.readFluidStack()
        val (result, resultAmount) = packet.readFluidStack()

        return FluidMixingAssembly(id, first, firstAmount, second, secondAmount, result, resultAmount, slots)
    }
}