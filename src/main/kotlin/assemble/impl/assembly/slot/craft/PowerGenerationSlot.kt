package assemble.impl.assembly.slot.craft

import assemble.api.assembly.slot.incremental.GenerationSlot
import assemble.impl.assembly.adapter.power.PowerAdapter
import assemble.impl.assembly.adapter.progress.ProgressAdapter
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import net.minecraft.network.PacketByteBuf

class PowerGenerationSlot<I>(override val quantity: Int) : GenerationSlot<Nothing, I>() where I : PowerAdapter, I : ProgressAdapter {
    override val resource: Nothing
        get() = throw IllegalStateException("Nonexistent resource was accessed.")

    override fun spaceAvailable(inventory: I): Boolean {
        return inventory.getPower() <= inventory.getPowerCapacity() - quantity
    }

    override fun generate(inventory: I) {
        inventory.addPower(quantity.toLong())
    }

    class Type<I> : GenerationSlot.Type<Nothing, I, PowerGenerationSlot<I>>() where I : PowerAdapter, I : ProgressAdapter {
        override fun read(json: JsonElement): PowerGenerationSlot<I> {
            return when(json) {
                is JsonPrimitive -> {
                    if (json.isNumber) {
                        PowerGenerationSlot(json.asInt)
                    } else {
                        throw unrecognizedFormat(json)
                    }
                }

                is JsonObject -> {
                    if (json["power"] != null && json["power"].isJsonPrimitive && json["power"].asJsonPrimitive.isNumber) {
                        PowerGenerationSlot(json["power"].asInt)
                    } else {
                        throw unrecognizedFormat(json)
                    }
                }

                else -> throw unrecognizedFormat(json)
            }
        }

        override fun unpack(buffer: PacketByteBuf): PowerGenerationSlot<I> {
            return PowerGenerationSlot(buffer.readInt())
        }

        override fun pack(buffer: PacketByteBuf, slot: PowerGenerationSlot<I>) {
            buffer.writeInt(slot.quantity)
        }
    }
}