package assemble.test.common

import assemble.test.common.content.TestContent
import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader
import nucleus.common.builtin.division.ModRoot

object AssembleTest : ModRoot<AssembleTest>("assemble"), ModInitializer {
    override val content = TestContent(this)

    override fun onInitialize() {
        if (FabricLoader.getInstance().isDevelopmentEnvironment) {
            launch(this)
        }
    }
}