package net.walksanator.hexxyskies.casting.patterns

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.misc.MediaConstants
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.block.Rotation.NONE
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.Vec3
import net.walksanator.hexxyskies.casting.VariableMediaAction
import net.walksanator.hexxyskies.casting.iotas.ShipIota
import org.joml.Vector3d
import org.valkyrienskies.core.impl.game.ships.ShipDataCommon
import org.valkyrienskies.core.impl.game.ships.ShipTransformImpl
import org.valkyrienskies.core.util.datastructures.DenseBlockPosSet
import org.valkyrienskies.mod.common.*
import org.valkyrienskies.mod.common.assembly.createNewShipWithBlocks
import org.valkyrienskies.mod.common.util.toBlockPos
import org.valkyrienskies.mod.common.util.toJOML
import org.valkyrienskies.mod.common.util.toJOMLD
import org.valkyrienskies.mod.util.relocateBlock
import kotlin.math.floor
import kotlin.math.ln

object OpAssemble : VariableMediaAction {
    override val argc: Int = 1
    override fun render(args: List<Iota>, env: CastingEnvironment): VariableMediaAction.Result {
        val blocks = args.getList(0, argc)
        val split = blocks.filterIsInstance<Vec3Iota>().filter { env.isVecInAmbit(it.vec3) }.map { it.vec3.blockPos }
            .partition { env.world.getShipManagingPos(it) == null }
        val filter = if (split.first.size >= split.second.size) {
            split.first
        } else {
            split.second
        }
        if (filter.isEmpty()) throw MishapInvalidIota.ofType(args[0], 0, "novalid_pos")
        return if (filter.size != 1) {
            Many(filter)
        } else {
            One(filter.first())
        }
    }

    private class Many(val blocks: List<BlockPos>) :
        VariableMediaAction.Result(((ln(blocks.size.toDouble()) * 3) + 1).toLong() * MediaConstants.CRYSTAL_UNIT) {
        override fun execute(env: CastingEnvironment): List<Iota> {
            val dense = DenseBlockPosSet()
            val center = blocks.fold(Vec3.ZERO) { acc, it ->
                val fix = it.toJOML()
                dense.add(fix)
                acc.add(it.center)
            }.div(blocks.size.toDouble()).blockPos

            val serverShip = createNewShipWithBlocks(center, dense, env.world)
            val blockPos = center
            val scale = 1.0
            val minScaling = 0.25
            val parentShip = env.world.getShipManagingPos(blocks.first())
            if (parentShip != null) {
                // Compute the ship transform
                val newShipPosInWorld =
                    parentShip.shipToWorld.transformPosition(blockPos.toJOMLD().add(0.5, .0, 0.5))
                val newShipPosInShipyard = blockPos.toJOMLD().add(0.5, 0.5, 0.5)
                val newShipRotation = parentShip.transform.shipToWorldRotation
                var newShipScaling = parentShip.transform.shipToWorldScaling.mul(scale, Vector3d())
                if (newShipScaling.x() < minScaling) {
                    // Do not allow scaling to go below minScaling
                    newShipScaling = Vector3d(minScaling, minScaling, minScaling)
                }
                val shipTransform =
                    ShipTransformImpl(newShipPosInWorld, newShipPosInShipyard, newShipRotation, newShipScaling)
                (serverShip as ShipDataCommon).transform = shipTransform
            }

            return listOf(ShipIota(serverShip.id, serverShip.slug))
        }
    }

    private class One(val blockPos: BlockPos) : VariableMediaAction.Result(MediaConstants.CRYSTAL_UNIT) {
        override fun execute(env: CastingEnvironment): List<Iota> {
            val level = env.world as ServerLevel
            val parentShip = level.getShipManagingPos(blockPos)
            // Make a ship
            val dimensionId = level.dimensionId
            val scale = 1.0
            val minScaling = 0.25
            val serverShip =
                level.shipObjectWorld.createNewShipAtBlock(blockPos.toJOML(), false, scale, dimensionId)
            val centerPos = serverShip.chunkClaim.getCenterBlockCoordinates(level.yRange).toBlockPos()
            // Move the block from the world to a ship
            level.relocateBlock(blockPos, centerPos, true, serverShip, NONE)
            if (parentShip != null) {
                // Compute the ship transform
                val newShipPosInWorld =
                    parentShip.shipToWorld.transformPosition(blockPos.toJOMLD().add(0.5, 0.5, 0.5))
                val newShipPosInShipyard = blockPos.toJOMLD().add(0.5, 0.5, 0.5)
                val newShipRotation = parentShip.transform.shipToWorldRotation
                var newShipScaling = parentShip.transform.shipToWorldScaling.mul(scale, Vector3d())
                if (newShipScaling.x() < minScaling) {
                    // Do not allow scaling to go below minScaling
                    newShipScaling = Vector3d(minScaling, minScaling, minScaling)
                }
                val shipTransform =
                    ShipTransformImpl(newShipPosInWorld, newShipPosInShipyard, newShipRotation, newShipScaling)
                (serverShip as ShipDataCommon).transform = shipTransform
            }
            return listOf(ShipIota(serverShip.id, serverShip.slug))
        }
    }
}

val Vec3.blockPos: BlockPos get() = BlockPos(floor(this.x).toInt(), floor(this.y).toInt(), floor(this.z).toInt())
fun Vec3.div(rhs: Double) = Vec3(this.x / rhs, this.y / rhs, this.z / rhs)