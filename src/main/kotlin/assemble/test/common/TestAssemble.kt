package assemble.test.common

import assemble.test.common.content.TestContent
import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader
import nucleus.common.builtin.division.ModRoot

object TestAssemble : ModRoot<TestAssemble>("assemble"), ModInitializer {
    override val instance = this
    override val content = TestContent(instance)

    override fun onInitialize() {
        if (FabricLoader.getInstance().isDevelopmentEnvironment) {
            launch()
        }
    }
}