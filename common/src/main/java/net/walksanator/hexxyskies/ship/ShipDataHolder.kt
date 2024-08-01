package net.walksanator.hexxyskies.ship

import com.fasterxml.jackson.annotation.JsonAutoDetect
import org.joml.Vector3d
import org.joml.Vector3dc
import org.valkyrienskies.core.api.ships.*
import org.valkyrienskies.core.util.pollUntilEmpty
import java.util.concurrent.ConcurrentLinkedQueue


@JsonAutoDetect
class ShipDataHolder() : ShipForcesInducer {
    // public changable values
    var forward: Vector3d = Vector3d(0.0, 0.0, -1.0)
    var cloaked: Boolean = false

    //stuff coped from vs2's GameTickForceApplier because they have a skill isssue
    private val invForces = ConcurrentLinkedQueue<Vector3dc>()
    private val invTorques = ConcurrentLinkedQueue<Vector3dc>()
    private val rotForces = ConcurrentLinkedQueue<Vector3dc>()
    private val rotTorques = ConcurrentLinkedQueue<Vector3dc>()
    private val invPosForces = ConcurrentLinkedQueue<InvForceAtPos>()

    //this one is custom beccause of a diffrent skill issue
    private val rotPosForces = ConcurrentLinkedQueue<RotDependentForce>()

    //more vs2 variables
    @Volatile
    var toBeStatic = false
        set(v) {
            field = v
            toBeStaticUpdated = true
        }

    @Volatile
    private var toBeStaticUpdated = false

    fun applyRotDependentForceToPos(force: Vector3dc, pos: Vector3dc) = rotPosForces.add(RotDependentForce(force, pos))

    companion object {
        fun getOrCreate(ship: ServerShip): ShipDataHolder =
            ship.getAttachment<ShipDataHolder>()
                ?: ShipDataHolder().also {
                    ship.saveAttachment(it)
                }
    }

    override fun applyForces(physShip: PhysShip) {
        rotPosForces.pollUntilEmpty { (force, pos) -> physShip.applyRotDependentForceToPos(force, pos) }
        //copied from vs2's GTFA
        invForces.pollUntilEmpty(physShip::applyInvariantForce)
        invTorques.pollUntilEmpty(physShip::applyInvariantTorque)
        rotForces.pollUntilEmpty(physShip::applyRotDependentForce)
        rotTorques.pollUntilEmpty(physShip::applyRotDependentTorque)
        invPosForces.pollUntilEmpty { (force, pos) -> physShip.applyInvariantForceToPos(force, pos) }

        if (toBeStaticUpdated) {
            physShip.isStatic = toBeStatic
            toBeStaticUpdated = false
        }
    }

    fun applyInvariantForce(force: Vector3dc) = invForces.add(force)
    fun applyInvariantTorque(torque: Vector3dc) = invTorques.add(torque)
    fun applyRotDependentForce(force: Vector3dc) = rotForces.add(force)
    fun applyRotDependentTorque(torque: Vector3dc) = rotTorques.add(torque)
    fun applyInvariantForceToPos(force: Vector3dc, pos: Vector3dc) = invPosForces.add(InvForceAtPos(force, pos))

    private data class RotDependentForce(val force: Vector3dc, val pos: Vector3dc)
    private data class InvForceAtPos(val force: Vector3dc, val pos: Vector3dc)
}

fun ServerShip.getShipDataHolder(): ShipDataHolder = ShipDataHolder.getOrCreate(this)