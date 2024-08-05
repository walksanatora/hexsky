package net.walksanator.hexxyskies.casting.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import net.walksanator.hexxyskies.getShip
import ram.talia.moreiotas.api.casting.iota.MatrixIota

object OpShipToWorldMatrix: ConstMediaAction {
    override val argc: Int
        get() = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val ship = args.getShip(0, env.world, argc)
        val matrix = ship.transform.shipToWorld.toDoubleMatrix()
        return listOf(MatrixIota(matrix))
    }
}