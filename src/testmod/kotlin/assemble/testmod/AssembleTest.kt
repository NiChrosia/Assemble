package assemble.testmod

import assemble.testmod.impl.registrar.TAssemblyTypeRegistrar
import net.fabricmc.api.ModInitializer

object AssembleTest : ModInitializer {
    const val namespace = "assemble"

    val assemblyTypes = TAssemblyTypeRegistrar(namespace)

    override fun onInitialize() {

    }
}