package net.walksanator.hexxyskies.casting.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.NullIota
import net.walksanator.hexxyskies.getInertialData
import net.walksanator.hexxyskies.getShip

object OpShipMass : ConstMediaAction {
    override val argc: Int = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val output = args.getShip(0,env.world,argc).getInertialData()?.mass?.let {DoubleIota(it)}?: NullIota()
        return listOf(output)
    }
}