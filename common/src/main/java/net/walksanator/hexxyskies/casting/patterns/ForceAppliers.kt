package net.walksanator.hexxyskies.casting.patterns

import at.petrak.hexcasting.api.casting.ParticleSpray
import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getVec3
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.misc.MediaConstants
import net.minecraft.world.phys.Vec3
import net.walksanator.hexxyskies.getInertialData
import net.walksanator.hexxyskies.getShip
import net.walksanator.hexxyskies.ship.ShipDataHolder
import net.walksanator.hexxyskies.ship.getShipDataHolder
import org.joml.Vector3dc
import org.valkyrienskies.core.api.ships.ServerShip
import org.valkyrienskies.core.api.ships.getAttachment
import org.valkyrienskies.core.impl.game.ships.ShipData
import org.valkyrienskies.mod.common.util.GameTickForceApplier
import org.valkyrienskies.mod.common.util.toJOML
import org.valkyrienskies.mod.common.util.toMinecraft
import kotlin.math.pow
import kotlin.math.sqrt

class OpForceApply(private val invariant: Boolean) : SpellAction {
    override val argc: Int = 2
    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val ship = args.getShip(0,env.world,argc)
        val force = args.getVec3(1,argc)
        return SpellAction.Result(
            Spell(invariant, ship.getShipDataHolder(), force.toJOML()),
            (force.length() / ship.mass).pow(2).toLong() * MediaConstants.DUST_UNIT,
            listOf(
                ParticleSpray(ship.getInertialData()!!.centerOfMassInShip.toMinecraft(), Vec3.ZERO, 0.3, 0.2, 30)
            )
        )
    }

    private class Spell(val invariant: Boolean,val fa: ShipDataHolder,val force: Vector3dc) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            if (invariant) {
                fa.applyInvariantForce(force)
            } else {
                fa.applyRotDependentForce(force)
            }
        }
    }
}

class OpTorqueApply(private val invariant: Boolean) : SpellAction {
    override val argc: Int = 2
    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val ship = args.getShip(0,env.world,argc)
        val force = args.getVec3(1,argc)
        return SpellAction.Result(

            Spell(invariant, ship.getShipDataHolder(), force.toJOML()),
            (force.length() / ship.mass).pow(2).toLong() * MediaConstants.DUST_UNIT,
            listOf(
                ParticleSpray(ship.getInertialData()!!.centerOfMassInShip.toMinecraft(), Vec3.ZERO, 0.3, 0.2, 30)
            )
        )
    }

    private class Spell(val invariant: Boolean,val fa: ShipDataHolder,val force: Vector3dc) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            if (invariant) {
                fa.applyInvariantTorque(force)
            } else {
                fa.applyRotDependentTorque(force)
            }
        }
    }
}

val ServerShip.mass: Double
    get() = this.getInertialData()!!.mass