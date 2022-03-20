package assemble.impl.assembly.item

import assemble.api.assembly.Assembly
import assemble.api.assembly.IncrementalAssembly
import assemble.impl.assembly.adapter.fluid.FluidAdapter
import assemble.impl.assembly.adapter.item.ItemAdapter
import assemble.impl.assembly.adapter.power.PowerAdapter
import assemble.impl.assembly.adapter.progress.ProgressAdapter
import assemble.impl.assembly.slot.consume.ItemIngredientSlot
import assemble.impl.assembly.slot.consume.PowerConsumptionSlot
import assemble.impl.assembly.slot.craft.FluidResultSlot
import com.google.gson.JsonObject
import net.minecraft.network.PacketByteBuf

class ElectricMelting<I>(
    val solid: ItemIngredientSlot<I>,
    val liquid: FluidResultSlot<I>,
    val power: PowerConsumptionSlot<I>,
) : IncrementalAssembly<I>(listOf(solid), listOf(liquid), listOf(power), listOf()) where I : ItemAdapter, I : FluidAdapter, I : PowerAdapter, I : ProgressAdapter {
    class Type<I>(solidSlot: Int, liquidSlot: Int) : Assembly.Type<I, ElectricMelting<I>>() where I : ItemAdapter, I : FluidAdapter, I : PowerAdapter, I : ProgressAdapter {
        val solid = ItemIngredientSlot.Type<I>(solidSlot)
        val liquid = FluidResultSlot.Type<I>(liquidSlot)
        val power = PowerConsumptionSlot.Type<I>()

        override fun read(json: JsonObject): ElectricMelting<I> {
            val solid = solid.read(json["solid"])
            val liquid = liquid.read(json["liquid"])
            val power = power.read(json["power"])

            return ElectricMelting(solid, liquid, power)
        }

        override fun unpack(buffer: PacketByteBuf): ElectricMelting<I> {
            val solid = solid.unpack(buffer)
            val liquid = liquid.unpack(buffer)
            val power = power.unpack(buffer)

            return ElectricMelting(solid, liquid, power)
        }

        override fun pack(buffer: PacketByteBuf, assembly: ElectricMelting<I>) {
            solid.pack(buffer, assembly.solid)
            liquid.pack(buffer, assembly.liquid)
            power.pack(buffer, assembly.power)
        }
    }
}