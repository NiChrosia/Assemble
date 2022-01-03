package assemble.common.type.impl.assembly.serializer

import assemble.common.type.api.assembly.serializer.FunctionalSlotSerializerType
import assemble.common.type.dsl.io.fluid.asFluidStack
import assemble.common.type.dsl.io.fluid.readFluidStack
import assemble.common.type.dsl.io.fluid.writeFluidStack
import assemble.common.type.impl.stack.FluidStack
import com.google.gson.JsonObject
import net.minecraft.network.PacketByteBuf

open class FluidStackType : FunctionalSlotSerializerType<FluidStack>(JsonObject::asFluidStack, PacketByteBuf::writeFluidStack, PacketByteBuf::readFluidStack)