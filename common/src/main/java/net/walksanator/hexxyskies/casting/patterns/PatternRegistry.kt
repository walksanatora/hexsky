package net.walksanator.hexxyskies.casting.patterns

import at.petrak.hexcasting.api.casting.ActionRegistryEntry
import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.common.lib.HexRegistries
import dev.architectury.registry.registries.DeferredRegister
import net.walksanator.hexxyskies.HexSkyCommon

object PatternRegistry {
    private val REGISTRY = DeferredRegister.create(HexSkyCommon.MOD_ID, HexRegistries.ACTION)

    val POS_TO_SHIP = REGISTRY.register("p2s"){ ActionRegistryEntry(
        HexPattern.fromAngles("wawwwa",HexDir.EAST),
        OpPosToShip
    )}

    val SHIP_POS = REGISTRY.register("ship_pos") { ActionRegistryEntry(
        HexPattern.fromAngles("wawwwawaa", HexDir.EAST),
        OpShipPos
    )}

    val SHIP_ROT = REGISTRY.register("ship_rot") { ActionRegistryEntry(
        HexPattern.fromAngles("wawwwaawa", HexDir.EAST),
        OpShipRot
    )}

    val APPLY_FORCE_VARIANT = REGISTRY.register("force_variant") { ActionRegistryEntry(
        HexPattern.fromAngles("wawwwawawwqqqwwaq", HexDir.EAST),
        OpForceApply(false)
    )}
    val APPLY_TORQUE_VARIANT = REGISTRY.register("torque_variant") { ActionRegistryEntry(
        HexPattern.fromAngles("wawwwawawwqqqwwawa", HexDir.EAST),
        OpTorqueApply(false)
    )}

    fun register() {
        REGISTRY.register()
    }
}