package assemble.common.type.dsl.io.fluid

import assemble.common.type.dsl.io.asEntry
import assemble.common.type.impl.stack.FluidStack
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.util.registry.Registry

val JsonElement.asFluid: FluidVariant
    get() {
        return FluidVariant.of(asEntry(Registry.FLUID))
    }

val JsonObject.asFluidStack: FluidStack
    get() {
        val type = this["fluid"].asFluid
        val amount = this["amount"].asLong

        return FluidStack(type, amount)
    }