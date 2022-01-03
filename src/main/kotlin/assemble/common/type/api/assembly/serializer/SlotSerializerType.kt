package assemble.common.type.api.assembly.serializer

import com.google.gson.JsonObject
import net.minecraft.network.PacketByteBuf

interface SlotSerializerType<T> {
    fun read(json: JsonObject): T

    fun pack(buffer: PacketByteBuf, element: T)
    fun unpack(buffer: PacketByteBuf): T
}