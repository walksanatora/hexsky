package net.walksanator.hexxyskies.casting.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.NullIota
import net.walksanator.hexxyskies.getShip
import ram.talia.moreiotas.api.casting.iota.StringIota

object OpShipGetSlug: ConstMediaAction {
    override val argc: Int
        get() = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val slug = args.getShip(0, env.world, argc).slug ?: return listOf(NullIota())
        return listOf(StringIota.make(slug))
    }
}