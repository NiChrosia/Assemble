package assemble.common.type.api.assembly.serializer

import com.google.gson.JsonObject
import net.minecraft.network.PacketByteBuf

open class FunctionalSlotSerializerType<T>(
    val reader: (JsonObject) -> T,
    val packer: (PacketByteBuf, T) -> Unit,
    val unpacker: (PacketByteBuf) -> T
) : SlotSerializerType<T> {
    override fun read(json: JsonObject): T {
        return reader(json)
    }

    override fun pack(buffer: PacketByteBuf, element: T) {
        packer(buffer, element)
    }

    override fun unpack(buffer: PacketByteBuf): T {
        return unpacker(buffer)
    }
}