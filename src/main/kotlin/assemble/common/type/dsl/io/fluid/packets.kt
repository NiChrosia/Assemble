package assemble.common.type.dsl.io.fluid

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.registry.Registry

fun PacketByteBuf.readFluid(): Pair<FluidVariant, Long> {
    val id = readIdentifier()
    val fluid = Registry.FLUID[id]
    val variant = FluidVariant.of(fluid)

    val amount = readLong()

    return variant to amount
}

fun PacketByteBuf.writeFluid(fluid: FluidVariant, amount: Long) {
    writeIdentifier(Registry.FLUID.getId(fluid.fluid))
    writeLong(amount)
}