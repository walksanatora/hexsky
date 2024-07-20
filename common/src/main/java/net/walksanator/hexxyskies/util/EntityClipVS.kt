package org.valkyrienskies.mod.common.world

import at.petrak.hexcasting.common.casting.actions.raycast.OpEntityRaycast
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3
import org.joml.primitives.AABBd
import org.joml.primitives.AABBdc
import org.valkyrienskies.core.api.ships.ClientShip
import org.valkyrienskies.mod.common.shipObjectWorld
import org.valkyrienskies.mod.common.util.toJOML
import org.valkyrienskies.mod.common.util.toMinecraft
import java.util.function.Predicate

@JvmOverloads
fun Level.entityClipIncludeShips(
    ent: Entity,
    startPos: Vec3, endPos: Vec3,
    isValid: Predicate<Entity>, maxSqrLength: Double, shouldTransformHitPos: Boolean = true,
): EntityHitResult? {

    val vanillaHit: EntityHitResult? = OpEntityRaycast.getEntityHitResult(ent,this, startPos,endPos,AABB(startPos,endPos), isValid, maxSqrLength)

    var closestHit = vanillaHit
    var closestHitPos = vanillaHit?.location?: Vec3(10.0,10.0,10.0).multiply(100.0,100.0,100.0)
    var closestHitDist = closestHitPos.distanceToSqr(startPos)

    val clipAABB: AABBdc = AABBd(endPos.toJOML(), startPos.toJOML()).correctBounds()

    // Iterate every ship, find do the raycast in ship space,
    // choose the raycast with the lowest distance to the start position.
    for (ship in shipObjectWorld.loadedShips.getIntersecting(clipAABB)) {
        val worldToShip = (ship as? ClientShip)?.renderTransform?.worldToShip ?: ship.worldToShip
        val shipToWorld = (ship as? ClientShip)?.renderTransform?.shipToWorld ?: ship.shipToWorld
        val shipStart = worldToShip.transformPosition(startPos.toJOML()).toMinecraft()
        val shipEnd = worldToShip.transformPosition(endPos.toJOML()).toMinecraft()

        val shipHit = OpEntityRaycast.getEntityHitResult(ent, this, shipStart, shipEnd, AABB(shipStart,shipEnd), isValid, maxSqrLength)
            ?: continue
        val shipHitPos = shipToWorld.transformPosition(shipHit.location.toJOML()).toMinecraft()
        val shipHitDist = shipHitPos.distanceToSqr(startPos)

        if (shipHitDist < closestHitDist && shipHit.type != HitResult.Type.MISS) {
            closestHit = shipHit
            closestHitPos = shipHitPos
            closestHitDist = shipHitDist
        }
    }

    if (shouldTransformHitPos && closestHit != null) {
        closestHit = EntityHitResult(closestHit.entity,closestHitPos)
    }

    return closestHit
}

