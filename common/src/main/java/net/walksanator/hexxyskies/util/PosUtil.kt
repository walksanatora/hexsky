package net.walksanator.hexxyskies.util

import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import org.valkyrienskies.mod.common.getShipManagingPos
import org.valkyrienskies.mod.common.toWorldCoordinates

fun Vec3.worldly(level: Level): Vec3 =
    level.getShipManagingPos(this)?.toWorldCoordinates(this)?: this
