package net.walksanator.hexxyskies.casting.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import net.walksanator.hexxyskies.getShip
import org.valkyrienskies.mod.common.util.toMinecraft

object OpShipScale : ConstMediaAction {
    override val argc: Int = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val scale = args.getShip(0,env.world,argc).transform.shipToWorldScaling.x()
        return listOf(DoubleIota(scale))
    }
}