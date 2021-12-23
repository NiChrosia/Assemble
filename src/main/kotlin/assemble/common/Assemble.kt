package assemble.common

import assemble.common.content.AssembleContent
import assemble.common.type.api.assembly.AssemblyManager
import assemble.common.type.impl.packet.AssemblySyncPacket
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.minecraft.resource.ResourceType
import net.minecraft.util.Identifier
import nucleus.common.builtin.division.ModRoot

object Assemble : ModRoot<Assemble>("assemble"), ModInitializer {
    override val instance = this
    override val content = AssembleContent(instance)

    val manager = AssemblyManager(Identifier("assemble", "assembly_manager"))

    override fun onInitialize() {
        launch()

        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(manager)

        ServerPlayConnectionEvents.JOIN.register { handler, sender, server ->
            AssemblySyncPacket.send(handler, sender, server)
        }
    }
}