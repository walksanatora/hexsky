package net.walksanator.hexxyskies.casting.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import net.walksanator.hexxyskies.getShip
import net.walksanator.hexxyskies.ship.getShipDataHolder
import org.joml.Vector3d
import org.valkyrienskies.core.api.ships.ServerShip
import org.valkyrienskies.mod.common.util.toMinecraft

object OpShipRot : ConstMediaAction {
    override val argc: Int = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val ship = args.getShip(0,env.world,argc)
        val forward = ship.transform.shipToWorldRotation
            .transform(ship.getShipDataHolder().forward ).toMinecraft()
        return listOf(Vec3Iota(forward))
    }
}