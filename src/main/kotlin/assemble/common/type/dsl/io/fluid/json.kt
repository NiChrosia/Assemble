package assemble.common.type.dsl.io.fluid

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

val JsonObject.asFluid: Pair<FluidVariant, Long>
    get() {
        val idString = this["fluid"].asString
        val id = Identifier(idString)
        val fluid = Registry.FLUID[id]
        val variant = FluidVariant.of(fluid)

        val amount = this["amount"].asLong

        return variant to amount
    }

val JsonElement.asFluid: Pair<FluidVariant, Long>
    get() = asJsonObject.asFluid