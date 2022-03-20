package assemble.testmod.impl.block.entity

import assemble.Assemble
import assemble.impl.assembly.adapter.fluid.FluidAdapter
import assemble.impl.assembly.adapter.item.InventoryAdapter
import assemble.impl.assembly.adapter.power.PowerAdapter
import assemble.impl.assembly.adapter.progress.ProgressAdapter
import assemble.impl.assembly.item.ElectricMelting
import assemble.testmod.AssembleTest
import assemble.testmod.impl.storage.fluid.SlottedFluidStorage
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import net.minecraft.world.World

@Suppress("UnstableApiUsage")
class TestBlockEntity(
    pos: BlockPos,
    state: BlockState
) : BlockEntity(AssembleTest.blockEntityTypes.test, pos, state), Inventory by SimpleInventory(2), InventoryAdapter, FluidAdapter by SlottedFluidStorage(2, FluidConstants.BUCKET), PowerAdapter, ProgressAdapter {
    private var power = 0L
    private var progress = 0

    private val assemblyType = AssembleTest.assemblyTypes.electricMelting

    var assembly: ElectricMelting<TestBlockEntity>? = null

    // bootleg power because I don't want TR Energy as a dependency
    override fun getPower(): Long {
        return power
    }

    override fun setPower(power: Long) {
        this.power = power
    }

    override fun getPowerCapacity(): Long {
        return 10_000L
    }

    override fun getIncrement(): Int {
        return 1
    }

    override fun getProgress(): Int {
        return progress
    }

    override fun setProgress(progress: Int) {
        this.progress = progress
    }

    override fun getMaxProgress(): Int {
        return 200
    }

    override fun markDirty() {
        super.markDirty()

        if (world?.isClient == false) {
            // only reset the assembly if changes are made
            assembly = Assemble.loader.firstMatching(assemblyType, this)
        }
    }

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)

        val items = NbtCompound()
        val fluids = NbtCompound()

        for (slot in 0..1) {
            val stack = getStack(slot)
            val stackNbt = stack.writeNbt(NbtCompound())
            items.put("$slot", stackNbt)

            val fluid = getFluid(slot)
            val amount = getAmount(slot)
            val fluidNbt = NbtCompound()
            fluidNbt.putString("Id", Registry.FLUID.getId(fluid).toString())
            fluidNbt.putLong("Amount", amount)

            fluids.put("$slot", fluidNbt)
        }

        items.putInt("Size", 2)
        fluids.putInt("Size", 2)

        nbt.put("Items", items)
        nbt.put("Fluids", fluids)
        nbt.putLong("Power", power)
        nbt.putInt("Progress", progress)
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)

        val items = nbt.getCompound("Items")
        val itemsSize = items.getInt("Size")

        for (slot in 0 until itemsSize) {
            val stackNbt = items.getCompound("$slot")
            val stack = ItemStack.fromNbt(stackNbt)

            setStack(slot, stack)
        }

        val fluids = nbt.getCompound("Fluids")
        val fluidsSize = fluids.getInt("Size")

        for (slot in 0 until fluidsSize) {
            val fluidNbt = fluids.getCompound("$slot")
            val fluid = Registry.FLUID[Identifier(fluidNbt.getString("Id"))]
            val amount = fluidNbt.getLong("Amount")

            setFluid(slot, fluid)
            setAmount(slot, amount)
        }

        power = nbt.getLong("Power")
        progress = nbt.getInt("Progress")

        if (world?.isClient == false) {
            // only reset the assembly if changes are made
            assembly = Assemble.loader.firstMatching(assemblyType, this)
        }
    }

    fun tick(world: World, pos: BlockPos, state: BlockState) {
         if (!world.isClient && assembly != null) {
             val safeAssembly = assembly!!

             if (safeAssembly.matches(this)) {
                 safeAssembly.increment(this)
                 safeAssembly.checkProgress(this)
             } else {
                 assembly = null
             }
         }
    }
}