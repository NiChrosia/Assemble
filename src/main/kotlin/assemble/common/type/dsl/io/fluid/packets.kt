package assemble.common.type.dsl.io.fluid

import assemble.common.type.dsl.io.readEntry
import assemble.common.type.dsl.io.writeEntry
import assemble.common.type.impl.stack.FluidStack
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.registry.Registry

fun PacketByteBuf.readFluid(): FluidVariant {
    val fluid = readEntry(Registry.FLUID)

    return FluidVariant.of(fluid)
}

fun PacketByteBuf.readFluidStack(): FluidStack {
    val type = readFluid()
    val amount = readLong()

    return FluidStack(type, amount)
}

fun PacketByteBuf.writeFluid(variant: FluidVariant) {
    writeEntry(Registry.FLUID, variant.fluid)
}

fun PacketByteBuf.writeFluidStack(stack: FluidStack) {
    writeFluid(stack.type)
    writeLong(stack.amount)
}