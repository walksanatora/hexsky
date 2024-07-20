package net.walksanator.hexxyskies.casting

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.CastingEnvironmentComponent
import at.petrak.hexcasting.api.casting.eval.CastingEnvironmentComponent.IsVecInRange
import net.minecraft.world.phys.Vec3
import net.walksanator.hexxyskies.mixin.CastingEnvironmentAccessor
import org.valkyrienskies.mod.common.getShipManagingPos
import org.valkyrienskies.mod.common.toWorldCoordinates
import kotlin.random.Random

class AmbitViaRemap(val parent: CastingEnvironment) : IsVecInRange {
    val id = Keygen.randid()
    val key = Key(id)
    override fun getKey(): CastingEnvironmentComponent.Key<*> = key
    class Key(val id: Int) : CastingEnvironmentComponent.Key<AmbitViaRemap>

    override fun onIsVecInRange(vec: Vec3, current: Boolean): Boolean {
        if (current) {return true}
        val ship = parent.world.getShipManagingPos(vec)
        //parent.world.allShips.getById();
        return (parent as CastingEnvironmentAccessor).invokeIsVecInRangeEnvironment(ship?.toWorldCoordinates(vec))
    }

    private object Keygen {
        val rand = Random(2819038190)
        fun randid() = rand.nextInt()

    }
}