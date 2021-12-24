package assemble.common.type.api.assembly.type

import assemble.common.type.api.assembly.Assembly
import com.google.gson.JsonObject
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier

/** A type of assembly, containing various methods for (de)serialization of assemblies. */
abstract class AssemblyType<C, out A : Assembly<C>>(val id: Identifier) {
    /** Deserialize the given [JsonObject] (from data/namespace/assemblies/recipe.json) into an assembly. */
    abstract fun deserialize(id: Identifier, json: JsonObject): A

    /** Pack the given assembly into a packet for syncing JSON assemblies. */
    abstract fun pack(packet: PacketByteBuf, assembly: @UnsafeVariance A)

    /** Unpack the given assembly from the server into an assembly. */
    abstract fun unpack(id: Identifier, packet: PacketByteBuf): A
}