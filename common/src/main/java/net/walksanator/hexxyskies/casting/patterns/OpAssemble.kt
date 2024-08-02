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
import org.valkyrienskies.core.util.datastructures.DenseBlockPosSet
import org.valkyrienskies.mod.common.assembly.createNewShipWithBlocks
import org.valkyrienskies.mod.common.util.toJOML
import kotlin.math.ln

object OpAssemble : VariableMediaAction {
    override val argc: Int = 1
    override fun render(args: List<Iota>, env: CastingEnvironment): VariableMediaAction.Result {
        val blocks = args.getList(0,argc).filterIsInstance<Vec3Iota>().filter { env.isVecInAmbit(it.vec3) }.map {it.vec3}
        return Spell(blocks)
    }

    private class Spell(val blocks: List<Vec3>) : VariableMediaAction.Result(((ln(blocks.size.toDouble()) * 3) + 1).toLong() * blocks.size * MediaConstants.CRYSTAL_UNIT ) {
        override fun execute(env: CastingEnvironment): List<Iota> {
            val dense = DenseBlockPosSet()
            val center = blocks.fold(Vec3.ZERO) { acc, it ->
                dense.add(it.blockPos.toJOML())
                acc.add(it)
            }.blockPos

            val ship = createNewShipWithBlocks(center, dense, env.world)
            return listOf(ShipIota(ship.id,ship.slug))
        }

    }
}

val Vec3.blockPos: BlockPos get() = BlockPos(this.x.toInt(), this.y.toInt(), this.z.toInt())