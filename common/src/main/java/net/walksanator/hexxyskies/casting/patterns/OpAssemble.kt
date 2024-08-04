package net.walksanator.hexxyskies.casting.patterns

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.misc.MediaConstants
import net.minecraft.core.BlockPos
import net.minecraft.world.phys.Vec3
import net.walksanator.hexxyskies.casting.VariableMediaAction
import net.walksanator.hexxyskies.casting.iotas.ShipIota
import net.walksanator.hexxyskies.ship.getShipDataHolder
import org.joml.Vector3i
import org.valkyrienskies.core.util.datastructures.DenseBlockPosSet
import org.valkyrienskies.mod.common.assembly.createNewShipWithBlocks
import org.valkyrienskies.mod.common.util.toJOML
import ram.talia.hexal.api.div
import kotlin.math.ln

object OpAssemble : VariableMediaAction {
    override val argc: Int = 1
    override fun render(args: List<Iota>, env: CastingEnvironment): VariableMediaAction.Result {
        val blocks = args.getList(0,argc)
        val filter = blocks.filterIsInstance<Vec3Iota>().filter { env.isVecInAmbit(it.vec3) }.map {it.vec3}
        return Spell(filter)
    }

    private class Spell(val blocks: List<Vec3>) : VariableMediaAction.Result(((ln(blocks.size.toDouble()) * 3) + 1).toLong() * blocks.size * MediaConstants.CRYSTAL_UNIT ) {
        override fun execute(env: CastingEnvironment): List<Iota> {
            val dense = DenseBlockPosSet()
            val center = blocks.fold(Vec3.ZERO) { acc, it ->
                val fix = it.blockPos.toJOML()
                dense.add(if (fix.y() < 0) {fix.add(0,-1,0)} else {fix})
                acc.add(it)
            }.div(blocks.size.toDouble()).blockPos

            val ship = createNewShipWithBlocks(center, dense, env.world)
            println(ship.id)
            return listOf(ShipIota(ship.id,ship.slug))
        }

    }
}

val Vec3.blockPos: BlockPos get() = BlockPos(this.x.toInt(), this.y.toInt(), this.z.toInt())