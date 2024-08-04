package net.walksanator.hexxyskies.casting.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.NullIota
import dev.kineticcat.complexhex.api.casting.iota.QuaternionIota
import dev.kineticcat.complexhex.api.getQuaternion
import dev.kineticcat.complexhex.stuff.Quaternion
import net.walksanator.hexxyskies.casting.iotas.ShipIota
import net.walksanator.hexxyskies.getShip
import net.walksanator.hexxyskies.ship.getShipDataHolder
import org.joml.Quaterniond
import org.joml.Quaterniondc
import org.valkyrienskies.core.impl.game.ShipTeleportDataImpl
import org.valkyrienskies.mod.common.shipObjectWorld
import kotlin.math.abs

object OpShipSetRotQuat : ConstMediaAction {
    override val argc: Int = 2
    override val mediaCost: Long = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val ship = args.getShip(0,env.world,argc)
        val quat = args.getQuaternion(1, argc)
        if (ship.getShipDataHolder().cloaked || !quat.isValidRotation()) return listOf(NullIota())
        env.world.shipObjectWorld.teleportShip(ship, ShipTeleportDataImpl(newRot = quat.toJOML()))
        return listOf(ShipIota(ship.id, ship.slug))
    }
}

fun Quaternion.isValidRotation(): Boolean =
    abs(x * x + y * y + z * z + w * w - 1.0) < 0.01

fun Quaternion.toJOML(): Quaterniondc =
    Quaterniond(x, y, z, w)