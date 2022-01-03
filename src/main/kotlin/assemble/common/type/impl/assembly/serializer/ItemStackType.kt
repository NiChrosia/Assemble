package assemble.common.type.impl.assembly.serializer

import assemble.common.type.api.assembly.serializer.FunctionalSlotSerializerType
import assemble.common.type.dsl.io.item.asItemStack
import com.google.gson.JsonObject
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf

open class ItemStackType : FunctionalSlotSerializerType<ItemStack>(JsonObject::asItemStack, PacketByteBuf::writeItemStack, PacketByteBuf::readItemStack)