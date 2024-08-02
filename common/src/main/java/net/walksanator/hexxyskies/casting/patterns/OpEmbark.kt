package net.walksanator.hexxyskies.casting.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import net.walksanator.hexxyskies.getShip
import org.valkyrienskies.mod.common.util.toJOML
import org.valkyrienskies.mod.common.util.toMinecraft
import ram.talia.hexal.api.casting.eval.env.WispCastEnv

object OpEmbark : ConstMediaAction {
    override val argc: Int = 1
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val ship = args.getShip(0,env.world,argc)
        if (env is WispCastEnv) {
            val pos = ship.transform.worldToShip.transformPosition(env.wisp.position().toJOML()).toMinecraft()
            env.wisp.teleportTo(pos.x,pos.y,pos.z)
        }
        return listOf()
    }

}