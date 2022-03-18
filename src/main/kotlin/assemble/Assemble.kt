package assemble

import assemble.impl.assembly.loader.AssemblyLoader
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.minecraft.resource.ResourceType

object Assemble : ModInitializer {
    val loader = AssemblyLoader()

    override fun onInitialize() {
        val serverData = ResourceManagerHelper.get(ResourceType.SERVER_DATA)
        serverData.registerReloadListener(loader)
    }
}