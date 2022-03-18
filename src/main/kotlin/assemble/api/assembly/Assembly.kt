package assemble.api.assembly

import assemble.api.assembly.slot.ConsumeSlot
import assemble.api.assembly.slot.CraftingSlot
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.network.PacketByteBuf

abstract class Assembly<I>(val consumption: List<ConsumeSlot<*, I>>, val crafting: List<CraftingSlot<*, I>>) {
    open fun matches(inventory: I): Boolean {
        return crafting.all { it.spaceAvailable(inventory) } && consumption.all { it.enoughIngredients(inventory) }
    }

    open fun craft(inventory: I) {
        for (slot in crafting) slot.craft(inventory)
        for (slot in consumption) slot.consume(inventory)
    }

    abstract class Type<I, A : Assembly<I>> {
        abstract fun read(json: JsonObject): A

        abstract fun unpack(buffer: PacketByteBuf): A
        abstract fun pack(buffer: PacketByteBuf, assembly: A)
    }
}