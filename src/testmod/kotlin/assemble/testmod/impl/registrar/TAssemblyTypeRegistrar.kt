package assemble.testmod.impl.registrar

import assemble.impl.assembly.fluid.FluidTransmutation
import assemble.impl.assembly.item.Transmutation
import assemble.impl.registrar.template.AssemblyTypeRegistrar
import assemble.testmod.impl.block.entity.AdaptedChestBlockEntity
import assemble.testmod.impl.storage.fluid.SlottedFluidStorage

open class TAssemblyTypeRegistrar(namespace: String) : AssemblyTypeRegistrar(namespace) {
    val transmutation = register("transmutation", Transmutation.Type<AdaptedChestBlockEntity>(0, 1))
    val fluidTransmutation = register("fluid_transmutation", FluidTransmutation.Type<SlottedFluidStorage>(0, 1))
}