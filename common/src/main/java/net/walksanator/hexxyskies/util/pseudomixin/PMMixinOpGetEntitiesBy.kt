package net.walksanator.hexxyskies.util.pseudomixin

import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import net.walksanator.hexxyskies.util.worldly

object PMMixinOpGetEntitiesBy {
    @JvmStatic
    fun resort(ie: Iterable<Entity>, level: Level, pos: Vec3): List<Entity> = ie.sortedBy {
            it.position().worldly(level).distanceToSqr(pos)
    }
}