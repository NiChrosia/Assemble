package assemble.common.type.api.packet

import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayNetworkHandler
import net.minecraft.util.Identifier

abstract class ClientboundPacket {
    abstract val id: Identifier

    abstract fun receive(
        client: MinecraftClient,
        handler: ClientPlayNetworkHandler,
        packet: PacketByteBuf,
        sender: PacketSender
    )

    open fun send(
        handler: ServerPlayNetworkHandler,
        sender: PacketSender,
        server: MinecraftServer,
        packet: PacketByteBuf = PacketByteBuf(Unpooled.buffer())
    ) {
        ServerPlayNetworking.send(handler.player, id, packet)
    }

    open fun registerReceiver(id: Identifier) {
        ClientPlayNetworking.registerGlobalReceiver(id, this::receive)
    }
}