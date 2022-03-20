package assemble.api.assembly.slot

import com.google.gson.JsonElement
import net.minecraft.network.PacketByteBuf

abstract class AssemblySlot<R, I> internal constructor() {
    abstract val quantity: Int
    abstract val resource: R

    abstract class Type<R, I, S : AssemblySlot<R, I>> internal constructor() {
        fun unrecognizedFormat(json: JsonElement): IllegalArgumentException {
            val unrecognized = "Unrecognized json format '${json::class.simpleName}' "
            val forType = "for slot type '${this::class.simpleName}'"
            val content = "\nJson content: $json"

            return IllegalArgumentException(unrecognized + forType + content)
        }

        fun missingEntry(type: String): IllegalArgumentException {
            return IllegalArgumentException("Given json does not contain entry of type $type")
        }

        /** Deserialize the given [json] into a valid slot. Type ambiguity is for the purpose of allowing shortcuts. */
        abstract fun read(json: JsonElement): S

        abstract fun unpack(buffer: PacketByteBuf): S
        abstract fun pack(buffer: PacketByteBuf, slot: S)
    }
}