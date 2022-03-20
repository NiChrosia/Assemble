package assemble.impl.assembly.slot.consume

import assemble.api.assembly.slot.incremental.ConsumptionSlot
import assemble.impl.assembly.adapter.power.PowerAdapter
import assemble.impl.assembly.adapter.progress.ProgressAdapter
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import net.minecraft.network.PacketByteBuf

class PowerConsumptionSlot<I>(override val quantity: Int) : ConsumptionSlot<Nothing, I>() where I : PowerAdapter, I : ProgressAdapter {
    override val resource: Nothing
        get() = throw IllegalStateException("Nonexistent resource was accessed.")

    override fun enoughInput(inventory: I): Boolean {
        return inventory.getPower() >= quantity
    }

    override fun consume(inventory: I) {
        inventory.subtractPower(quantity.toLong())
    }

    class Type<I> : ConsumptionSlot.Type<Nothing, I, PowerConsumptionSlot<I>>() where I : PowerAdapter, I : ProgressAdapter {
        override fun read(json: JsonElement): PowerConsumptionSlot<I> {
            return when(json) {
                is JsonPrimitive -> {
                    if (json.isNumber) {
                        PowerConsumptionSlot(json.asInt)
                    } else {
                        throw unrecognizedFormat(json)
                    }
                }

                is JsonObject -> {
                    if (json["power"] != null && json["power"].isJsonPrimitive && json["power"].asJsonPrimitive.isNumber) {
                        PowerConsumptionSlot(json["power"].asInt)
                    } else {
                        throw unrecognizedFormat(json)
                    }
                }

                else -> throw unrecognizedFormat(json)
            }
        }

        override fun unpack(buffer: PacketByteBuf): PowerConsumptionSlot<I> {
            return PowerConsumptionSlot(buffer.readInt())
        }

        override fun pack(buffer: PacketByteBuf, slot: PowerConsumptionSlot<I>) {
            buffer.writeInt(slot.quantity)
        }
    }
}