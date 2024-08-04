package net.walksanator.hexxyskies.casting.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.NullIota
import net.walksanator.hexxyskies.casting.iotas.ShipIota
import net.walksanator.hexxyskies.getShip
import net.walksanator.hexxyskies.ship.getShipDataHolder
import ram.talia.moreiotas.api.getString

object OpShipSetSlug: ConstMediaAction {
    override val argc: Int
        get() = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val slug = args.getString(0, argc)
        val ship = args.getShip(1, env.world, argc)
        if (ship.getShipDataHolder().cloaked) return listOf(NullIota())
        ship.slug = slug
        return listOf(ShipIota(ship.id, ship.slug))
    }
}