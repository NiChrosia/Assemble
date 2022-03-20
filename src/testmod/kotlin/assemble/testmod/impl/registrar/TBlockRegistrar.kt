package assemble.testmod.impl.registrar

import assemble.testmod.impl.block.TestBlock
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Blocks
import net.minecraft.block.Material
import nucleus.common.impl.registrar.template.BlockRegistrar

class TBlockRegistrar(namespace: String) : BlockRegistrar(namespace) {
    val test = register("test", TestBlock(FabricBlockSettings.of(Material.STONE)))
}