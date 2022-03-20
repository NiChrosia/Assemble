package assemble.testmod

import assemble.testmod.impl.registrar.TAssemblyTypeRegistrar
import assemble.testmod.impl.registrar.TBlockEntityTypeRegistrar
import assemble.testmod.impl.registrar.TBlockRegistrar
import net.fabricmc.api.ModInitializer

object AssembleTest : ModInitializer {
    const val namespace = "assemble"

    val blocks = TBlockRegistrar(namespace)
    val blockEntityTypes = TBlockEntityTypeRegistrar(namespace)

    val assemblyTypes = TAssemblyTypeRegistrar(namespace)

    override fun onInitialize() {
        // unnecessary, loading is handling in the constructor
    }
}