package net.walksanator.hexxyskies.casting.patterns

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.misc.MediaConstants
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.phys.Vec3
import net.walksanator.hexxyskies.casting.VariableMediaAction
import net.walksanator.hexxyskies.casting.iotas.ShipIota
import org.valkyrienskies.core.util.datastructures.DenseBlockPosSet
import org.valkyrienskies.mod.common.assembly.createNewShipWithBlocks
import org.valkyrienskies.mod.common.dimensionId
import org.valkyrienskies.mod.common.shipObjectWorld
import org.valkyrienskies.mod.common.util.toBlockPos
import org.valkyrienskies.mod.common.util.toJOML
import org.valkyrienskies.mod.common.yRange
import org.valkyrienskies.mod.util.relocateBlock
import kotlin.math.floor
import kotlin.math.ln

object OpAssemble : VariableMediaAction {
    override val argc: Int = 1
    override fun render(args: List<Iota>, env: CastingEnvironment): VariableMediaAction.Result {
        val blocks = args.getList(0,argc)
        val filter = blocks.filterIsInstance<Vec3Iota>().filter { env.isVecInAmbit(it.vec3) }.map {it.vec3.blockPos}
        if (filter.isEmpty()) throw MishapInvalidIota.ofType(args[0],0,"novalid_pos")
        return if (filter.size != 1) {Many(filter)} else {One(filter.first())}
    }

    private class Many(val blocks: List<BlockPos>) : VariableMediaAction.Result(((ln(blocks.size.toDouble()) * 3) + 1).toLong() * blocks.size * MediaConstants.CRYSTAL_UNIT ) {
        override fun execute(env: CastingEnvironment): List<Iota> {
            val dense = DenseBlockPosSet()
            val center = blocks.fold(Vec3.ZERO) { acc, it ->
                val fix = it.toJOML()
                dense.add(fix)
                acc.add(it.center)
            }.div(blocks.size.toDouble()).blockPos

            val ship = createNewShipWithBlocks(center, dense, env.world)
            println(ship.id)
            return listOf(ShipIota(ship.id,ship.slug))
        }
    }

    private class One(val block: BlockPos) : VariableMediaAction.Result(MediaConstants.CRYSTAL_UNIT) {
        override fun execute(env: CastingEnvironment): List<Iota> {
            val fix = block.toJOML()
            val ship = env.world.shipObjectWorld.createNewShipAtBlock(fix, false, 1.0, env.world.dimensionId)
            val center = ship.chunkClaim.getCenterBlockCoordinates(env.world.yRange).toBlockPos()
            env.world.relocateBlock(fix.toBlockPos(), center, true, ship, Rotation.NONE)
            return listOf(ShipIota(ship.id, ship.slug))
        }
    }
}

val Vec3.blockPos: BlockPos get() = BlockPos(floor(this.x).toInt(), floor(this.y).toInt(), floor(this.z).toInt())
fun Vec3.div(rhs: Double) = Vec3(this.x / rhs, this.y / rhs, this.z / rhs)