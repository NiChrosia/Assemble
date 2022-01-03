package assemble.common.type.api.assembly.serializer

import com.google.gson.JsonObject
import net.minecraft.network.PacketByteBuf

open class SlotSerializer<T>(val type: SlotSerializerType<T>, val key: String) {
    operator fun invoke(json: JsonObject): T {
        return type.read(json[key].asJsonObject)
    }

    operator fun invoke(buffer: PacketByteBuf, element: T) {
        type.pack(buffer, element)
    }

    operator fun invoke(buffer: PacketByteBuf): T {
        return type.unpack(buffer)
    }
}