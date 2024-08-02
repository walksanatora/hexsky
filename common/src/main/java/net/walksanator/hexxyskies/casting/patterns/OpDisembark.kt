package net.walksanator.hexxyskies.casting.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadEntity
import org.valkyrienskies.mod.common.getShipManagingPos
import org.valkyrienskies.mod.common.shipObjectWorld
import org.valkyrienskies.mod.common.toWorldCoordinates
import ram.talia.hexal.api.casting.eval.env.WispCastEnv

object OpDisembark : ConstMediaAction {
    override val argc: Int = 0
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        if (env is WispCastEnv) {
            val ship = env.world.getShipManagingPos(env.wisp.position())
            ship?.toWorldCoordinates(env.wisp.position())?.let {env.wisp.teleportTo(it.x,it.y,it.z)}
        }
        return listOf()
    }
}