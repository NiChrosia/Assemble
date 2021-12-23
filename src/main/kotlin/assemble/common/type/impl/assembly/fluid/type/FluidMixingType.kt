package assemble.common.type.impl.assembly.fluid.type

import assemble.common.type.api.assembly.type.AssemblyType
import assemble.common.type.dsl.io.fluid.asFluid
import assemble.common.type.dsl.io.fluid.readFluid
import assemble.common.type.dsl.io.fluid.writeFluid
import assemble.common.type.impl.assembly.fluid.FluidMixingAssembly
import assemble.common.type.impl.storage.fluid.FluidInventory
import com.google.gson.JsonObject
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier

open class FluidMixingType<C : FluidInventory>(id: Identifier) : AssemblyType<C, FluidMixingAssembly<C>>(id) {
    override fun deserialize(id: Identifier, json: JsonObject): FluidMixingAssembly<C> {
        val (first, firstAmount) = json["first"].asFluid
        val (second, secondAmount) = json["second"].asFluid
        val (result, resultAmount) = json["result"].asFluid

        return FluidMixingAssembly(id, first, firstAmount, second, secondAmount, result, resultAmount)
    }

    override fun pack(packet: PacketByteBuf, assembly: FluidMixingAssembly<C>) {
        packet.writeFluid(assembly.first, assembly.firstAmount)
        packet.writeFluid(assembly.second, assembly.secondAmount)
        packet.writeFluid(assembly.result, assembly.resultAmount)
    }

    override fun unpack(id: Identifier, packet: PacketByteBuf): FluidMixingAssembly<C> {
        val (first, firstAmount) = packet.readFluid()
        val (second, secondAmount) = packet.readFluid()
        val (result, resultAmount) = packet.readFluid()

        return FluidMixingAssembly(id, first, firstAmount, second, secondAmount, result, resultAmount)
    }
}