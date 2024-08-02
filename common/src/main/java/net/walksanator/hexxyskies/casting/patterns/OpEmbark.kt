package net.walksanator.hexxyskies.casting.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadLocation
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import net.walksanator.hexxyskies.casting.iotas.ShipIota
import org.valkyrienskies.mod.common.getShipManagingPos
import org.valkyrienskies.mod.common.util.toJOML
import org.valkyrienskies.mod.common.util.toMinecraft
import ram.talia.hexal.api.casting.eval.env.WispCastEnv

object OpEmbark : ConstMediaAction {
    override val argc: Int = 1
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val ship = when (val iota = args[0]) {
            is ShipIota -> iota.getShip(env.world)!!
            is Vec3Iota -> env.world.getShipManagingPos(iota.vec3.toJOML())?: throw MishapInvalidIota.ofType(iota,0,"pos_on_ship")
            else -> throw MishapInvalidIota.ofType(iota,0,"pos_or_ship")
        }
        if (env is WispCastEnv) {
            val pos = ship.transform.worldToShip.transformPosition(env.wisp.position().toJOML()).toMinecraft()
            if (!ship.shipAABB!!.toMinecraft().inflate(4.0).contains(pos)) throw MishapBadLocation(pos,"near_ship")
            env.wisp.teleportTo(pos.x,pos.y,pos.z)
        }
        return listOf()
    }

}