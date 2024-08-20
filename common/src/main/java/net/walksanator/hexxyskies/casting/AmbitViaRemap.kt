package net.walksanator.hexxyskies.casting

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.CastingEnvironmentComponent
import at.petrak.hexcasting.api.casting.eval.CastingEnvironmentComponent.IsVecInRange
import net.minecraft.world.phys.Vec3
import net.walksanator.hexxyskies.HexSkyCommon
import net.walksanator.hexxyskies.duck.ShipGetterEnvironment
import net.walksanator.hexxyskies.mixin.CastingEnvironmentAccessor
import org.valkyrienskies.mod.common.getShipManagingPos
import org.valkyrienskies.mod.common.toWorldCoordinates
import org.valkyrienskies.mod.common.util.toJOML
import org.valkyrienskies.mod.common.util.toMinecraft
import kotlin.random.Random

class Key(val id: Int) : CastingEnvironmentComponent.Key<AmbitViaRemap>

class AmbitViaRemap(private val parent: CastingEnvironment) : IsVecInRange {
    private val id = Keygen.randid()
    private val key = Key(id)
    override fun getKey(): CastingEnvironmentComponent.Key<*> = key
    override fun onIsVecInRange(vec: Vec3, current: Boolean): Boolean {
        if (current) {return true}
        if (parent is ShipGetterEnvironment) {
            val ship = parent.`hexsky$getShip`()
            if (ship == null) {
                val ship2 = parent.world.getShipManagingPos(vec)
                return (parent as CastingEnvironmentAccessor).invokeIsVecInRangeEnvironment(ship2?.toWorldCoordinates(vec))
                // ship to world remapping
            } else {
                // world to ship remapping
                return (parent as CastingEnvironmentAccessor).invokeIsVecInRangeEnvironment(
                    ship.worldToShip.transformPosition(vec.toJOML()).toMinecraft()
                )
            }
        } else {
            HexSkyCommon.LOGGER.warning("Environment %s does not implement ShipGetterEnvironment. make a issue if you see this!".format(parent.javaClass.canonicalName))
            HexSkyCommon.LOGGER.info("Falling back to ship to world remapping")
            val ship = parent.world.getShipManagingPos(vec)
            return (parent as CastingEnvironmentAccessor).invokeIsVecInRangeEnvironment(ship?.toWorldCoordinates(vec))
        }
    }

    private object Keygen {
        val rand = Random(2819038190)
        fun randid() = rand.nextInt()
    }
}