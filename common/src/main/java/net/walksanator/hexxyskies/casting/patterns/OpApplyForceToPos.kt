package net.walksanator.hexxyskies.casting.patterns

import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getVec3
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadLocation
import net.walksanator.hexxyskies.getInertialData
import net.walksanator.hexxyskies.getShip
import net.walksanator.hexxyskies.ship.ShipDataHolder
import net.walksanator.hexxyskies.ship.getShipDataHolder
import org.joml.Vector3dc
import org.joml.Vector3f
import org.joml.Vector3fc
import org.valkyrienskies.mod.common.util.toJOML

class OpApplyForceToPos(private val mode: Boolean) : SpellAction {
    override val argc: Int = 3
    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val ship = args.getShip(0, env.world, argc)
        val force = args.getVec3(1, argc)
        val offset = args.getVec3(2, argc)
        val point = Vector3f(0.0f,0.0f,0.0f)
        ship.getInertialData()!!.centerOfMassInShip.float.add(offset.toJOML().float,point)

        if (ship.shipAABB?.containsPoint(point) != true) {
            throw MishapBadLocation(offset, "out_of_ship")
        }

        return SpellAction.Result(Spell(mode, ship.getShipDataHolder(), force.toJOML(), offset.toJOML()), 0, listOf())
    }

    private class Spell(val mode: Boolean, val dh: ShipDataHolder, val force: Vector3dc, val offset: Vector3dc) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            if (mode) {
                dh.applyInvariantForceToPos(force,offset)
            } else {
                dh.applyInvariantTorqueToPos(force,offset)
            }
        }

    }
}

val Vector3dc.float: Vector3fc
    get() = Vector3f(this.x().toFloat(), this.y().toFloat(), this.z().toFloat())