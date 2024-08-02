package net.walksanator.hexxyskies.casting.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getVec3
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.misc.MediaConstants
import net.walksanator.hexxyskies.getShip
import net.walksanator.hexxyskies.ship.getShipDataHolder
import org.valkyrienskies.mod.common.util.toJOML
import org.valkyrienskies.mod.common.util.toMinecraft

object OpSetForward : ConstMediaAction {
    override val argc: Int = 2
    override val mediaCost: Long = MediaConstants.CRYSTAL_UNIT
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val ship = args.getShip(0,env.world,argc)
        val forward = args.getVec3(1,argc).normalize()
        ship.getShipDataHolder().forward = forward.toJOML()
        return listOf()
    }
}

object OpGetForward : ConstMediaAction {
    override val argc: Int = 1;
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val ship = args.getShip(0,env.world,argc)
        return listOf(Vec3Iota(ship.getShipDataHolder().forward.toMinecraft()))
    }
}