package assemble.common.type.impl.packet

import assemble.common.Assemble
import assemble.common.type.api.assembly.AssemblyManager.Companion.add
import assemble.common.type.api.packet.ClientboundPacket
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayNetworkHandler
import net.minecraft.util.Identifier

object AssemblySyncPacket : ClientboundPacket() {
    override val id = Identifier("assemble", "sync_assemblies")

    init {
        registerReceiver(id)
    }

    override fun receive(
        client: MinecraftClient,
        handler: ClientPlayNetworkHandler,
        packet: PacketByteBuf,
        sender: PacketSender
    ) {
        val entries = packet.readInt()

        for (entryIndex in 0 until entries) {
            val typeId = packet.readIdentifier()
            val type = Assemble.manager.types[typeId]!!

            val assembliesSize = packet.readInt()

            for (assemblyIndex in 0 until assembliesSize) {
                val assemblyId = packet.readIdentifier()
                val assembly = type.unpack(assemblyId, packet)

                Assemble.manager.assemblies.add(type, assembly)
            }
        }
    }

    override fun send(
        handler: ServerPlayNetworkHandler,
        sender: PacketSender,
        server: MinecraftServer,
        packet: PacketByteBuf
    ) {
        packet.writeInt(Assemble.manager.assemblies.size)

        Assemble.manager.assemblies.forEach { (type, assemblies) ->
            packet.writeIdentifier(type.id)
            packet.writeInt(assemblies.size)

            assemblies.forEach { assembly ->
                packet.writeIdentifier(assembly.id)

                type.pack(packet, assembly)
            }
        }

        super.send(handler, sender, server, packet)
    }
}