package assemble.common.type.dsl.io.fluid

import assemble.common.type.dsl.io.asEntry
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

val JsonElement.asFluid: FluidVariant
    get() {
        return FluidVariant.of(asEntry(Registry.FLUID))
    }

val JsonObject.asFluidStack: Pair<FluidVariant, Long>
    get() {
        val fluid = this["fluid"].asFluid
        val amount = this["amount"].asLong

        return fluid to amount
    }