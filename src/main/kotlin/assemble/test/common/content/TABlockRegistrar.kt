package assemble.test.common.content

import assemble.test.common.TestAssemble
import assemble.test.common.type.impl.world.block.TestBlock
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Material
import nucleus.common.builtin.division.content.BlockRegistrar

open class TABlockRegistrar(root: TestAssemble) : BlockRegistrar<TestAssemble>(root) {
    val test by memberOf(root.identify("test")) { TestBlock(FabricBlockSettings.of(Material.METAL)) }.apply {
        lang(::readableEnglish)
        model(::omnidirectionalModel)
        blockstate(::staticBlockstate)
    }
}