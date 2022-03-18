package assemble.api.assembly.slot

import com.google.gson.JsonElement
import net.minecraft.network.PacketByteBuf

abstract class AssemblySlot<R, in I> internal constructor() {
    abstract val quantity: Int
    abstract val resource: R

    abstract class Type<R, in I, S : AssemblySlot<R, I>> internal constructor() {
        fun unrecognizedFormat(): IllegalArgumentException {
            return IllegalArgumentException("Unrecognized json format for slot type '${this::class.simpleName}.'")
        }

        fun missingEntry(type: String): IllegalArgumentException {
            return IllegalArgumentException("Given json does not contain entry of type $type.")
        }

        /** Deserialize the given [json] into a valid slot. Type ambiguity is for the purpose of allowing shortcuts. */
        abstract fun read(json: JsonElement): AssemblySlot<R, I>

        abstract fun unpack(buffer: PacketByteBuf): S
        abstract fun pack(buffer: PacketByteBuf, slot: S)
    }
}