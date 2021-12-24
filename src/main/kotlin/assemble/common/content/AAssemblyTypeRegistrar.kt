package assemble.common.content

import assemble.common.Assemble
import assemble.common.type.api.registrar.AssemblyTypeRegistrar
import assemble.common.type.impl.assembly.item.type.ItemMergingType
import assemble.common.type.impl.assembly.item.type.ItemProcessingType
import net.minecraft.inventory.Inventory

open class AAssemblyTypeRegistrar(root: Assemble) : AssemblyTypeRegistrar<Assemble>(root) {
    val itemMerging by memberOf(root.identify("item_merging")) { ItemMergingType<Inventory>(it) }
    val itemProcessing by memberOf(root.identify("item_processing")) { ItemProcessingType<Inventory>(it) }
}