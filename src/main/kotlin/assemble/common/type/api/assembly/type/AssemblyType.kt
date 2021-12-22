package assemble.common.type.api.assembly.type

import assemble.common.type.api.assembly.Assembly
import com.google.gson.JsonObject
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier

abstract class AssemblyType<C, A : Assembly<C>>(val id: Identifier) {
    abstract fun deserialize(id: Identifier, json: JsonObject): A

    abstract fun pack(packet: PacketByteBuf, assembly: A)
    abstract fun unpack(id: Identifier, packet: PacketByteBuf): A
}