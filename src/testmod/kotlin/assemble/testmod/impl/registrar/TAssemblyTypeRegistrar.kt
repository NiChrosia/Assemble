package assemble.testmod.impl.registrar

import assemble.impl.assembly.fluid.FluidTransmutation
import assemble.impl.assembly.item.ElectricMelting
import assemble.impl.assembly.item.Transmutation
import assemble.impl.registrar.template.AssemblyTypeRegistrar
import assemble.testmod.impl.block.entity.TestBlockEntity

open class TAssemblyTypeRegistrar(namespace: String) : AssemblyTypeRegistrar(namespace) {
    val transmutation = register("transmutation", Transmutation.Type<TestBlockEntity>(0, 1))
    val fluidTransmutation = register("fluid_transmutation", FluidTransmutation.Type<TestBlockEntity>(0, 1))

    val electricMelting = register("electric_melting", ElectricMelting.Type<TestBlockEntity>(0, 0))
}