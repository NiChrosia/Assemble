package assemble.common.type.dsl.io.fluid

import assemble.common.type.dsl.io.readEntry
import assemble.common.type.dsl.io.writeEntry
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.registry.Registry

fun PacketByteBuf.readFluid(): FluidVariant {
    val fluid = readEntry(Registry.FLUID)

    return FluidVariant.of(fluid)
}

fun PacketByteBuf.readFluidStack(): Pair<FluidVariant, Long> {
    val variant = readFluid()
    val amount = readLong()

    return variant to amount
}

fun PacketByteBuf.writeFluid(variant: FluidVariant) {
    writeEntry(Registry.FLUID, variant.fluid)
}

fun PacketByteBuf.writeFluidStack(variant: FluidVariant, amount: Long) {
    writeFluid(variant)
    writeLong(amount)
}