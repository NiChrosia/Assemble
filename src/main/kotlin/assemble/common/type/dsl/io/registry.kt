package assemble.common.type.dsl.io

import com.google.gson.JsonElement
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier
import net.minecraft.util.registry.DefaultedRegistry

fun <T> JsonElement.asEntry(registry: DefaultedRegistry<T>): T {
    return registry.get(Identifier(asString))
}

fun <T> PacketByteBuf.readEntry(registry: DefaultedRegistry<T>): T {
    val id = readIdentifier()

    return registry.get(id)
}

fun <T> PacketByteBuf.writeEntry(registry: DefaultedRegistry<T>, entry: T) {
    val id = registry.getId(entry)

    writeIdentifier(id)
}