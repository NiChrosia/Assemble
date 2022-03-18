package assemble.api.assembly

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.network.PacketByteBuf

abstract class Assembly<I>(val consumption: List<Slot<*, I>>, val crafting: List<Slot<*, I>>) {
    open fun matches(inventory: I): Boolean {
        return crafting.all { it.spaceAvailable(inventory) } && consumption.all { it.enoughIngredients(inventory) }
    }

    open fun craft(inventory: I) {
        for (slot in crafting) slot.craft(inventory)
        for (slot in consumption) slot.consume(inventory)
    }

    abstract class Slot<T, in I> {
        abstract val quantity: Int
        abstract val resource: T

        // verification functions for craft & consume, respectively
        abstract fun spaceAvailable(inventory: I): Boolean
        abstract fun enoughIngredients(inventory: I): Boolean

        abstract fun craft(inventory: I)
        abstract fun consume(inventory: I)

        abstract class Type<T, in I, S : Slot<T, I>> {
            fun unrecognizedFormat(): IllegalArgumentException {
                return IllegalArgumentException("Unrecognized json format for slot type '${this::class.simpleName}.'")
            }

            fun missingEntry(type: String): IllegalArgumentException {
                return IllegalArgumentException("Given json does not contain entry of type $type.")
            }

            /** Deserialize the given [json] into a valid slot. Type ambiguity is for the purpose of allowing shortcuts. */
            abstract fun read(json: JsonElement): Slot<T, I>

            abstract fun unpack(buffer: PacketByteBuf): S
            abstract fun pack(buffer: PacketByteBuf, slot: S)
        }
    }

    abstract class Type<I, A : Assembly<I>> {
        abstract fun read(json: JsonObject): A

        abstract fun unpack(buffer: PacketByteBuf): A
        abstract fun pack(buffer: PacketByteBuf, assembly: A)
    }
}