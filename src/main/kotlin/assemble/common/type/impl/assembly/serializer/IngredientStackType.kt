package assemble.common.type.impl.assembly.serializer

import assemble.common.type.api.assembly.serializer.FunctionalSlotSerializerType
import assemble.common.type.dsl.io.item.asIngredientStack
import assemble.common.type.dsl.io.item.readIngredientStack
import assemble.common.type.dsl.io.item.writeIngredientStack
import assemble.common.type.impl.stack.IngredientStack
import com.google.gson.JsonObject
import net.minecraft.network.PacketByteBuf

open class IngredientStackType : FunctionalSlotSerializerType<IngredientStack>(JsonObject::asIngredientStack, PacketByteBuf::writeIngredientStack, PacketByteBuf::readIngredientStack)