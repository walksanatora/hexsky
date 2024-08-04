package net.walksanator.hexxyskies.casting.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import net.walksanator.hexxyskies.getShip
import org.jblas.DoubleMatrix
import org.joml.Matrix4dc
import ram.talia.moreiotas.api.casting.iota.MatrixIota

object OpWorldToShipMatrix: ConstMediaAction {
    override val argc: Int
        get() = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val ship = args.getShip(0, env.world, argc)
        val matrix = ship.transform.worldToShip.toDoubleMatrix()
        return listOf(MatrixIota(matrix))
    }
}

fun Matrix4dc.toDoubleMatrix(): DoubleMatrix {
    val matrix = DoubleMatrix(4, 4)
    for (row in 0..3) {
        for (col in 0..3) {
            matrix.put(row, col, this[col, row])
        }
    }
    return matrix
}