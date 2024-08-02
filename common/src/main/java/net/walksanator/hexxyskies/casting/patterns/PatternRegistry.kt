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
    val CLOAK = REGISTRY.register("cloak") { ActionRegistryEntry(
        HexPattern.fromAngles("dwwwdw", HexDir.NORTH_EAST),
        OpCloak
    )}

    val SHIP_POS = REGISTRY.register("ship_pos") { ActionRegistryEntry(
        HexPattern.fromAngles("wawwwawaa", HexDir.EAST),
        OpShipPos
    )}
    val SHIP_ROT = REGISTRY.register("ship_rot") { ActionRegistryEntry(
        HexPattern.fromAngles("wawwwaawa", HexDir.EAST),
        OpShipRot
    )}
    val SHIP_WEIGHT = REGISTRY.register("ship_weight") { ActionRegistryEntry(
        HexPattern.fromAngles("wawwwawde",HexDir.EAST),
        OpShipMass
    )}

    val APPLY_FORCE_VARIANT = REGISTRY.register("force_variant") { ActionRegistryEntry(
        HexPattern.fromAngles("wawwwawawwqqqwwaq", HexDir.EAST),
        OpForceApply(false)
    )}
    val APPLY_TORQUE_VARIANT = REGISTRY.register("torque_variant") { ActionRegistryEntry(
        HexPattern.fromAngles("wawwwawawwqqqwwawa", HexDir.EAST),
        OpTorqueApply(false)
    )}

    val APPLY_FORCE_INVARIANT = REGISTRY.register("force_invariant") { ActionRegistryEntry(
        HexPattern.fromAngles("wawwwawawwqqqwwaqw", HexDir.EAST),
        OpForceApply(true)
    )}
    val APPLY_TORQUE_INVARIANT = REGISTRY.register("torque_invariant") { ActionRegistryEntry(
        HexPattern.fromAngles("wawwwawawwqqqwwaqqd", HexDir.EAST),
        OpTorqueApply(true)
    )}

    val APPLY_FORCE_INVARIANT_POS = REGISTRY.register("force_invariant_pos") { ActionRegistryEntry(
        HexPattern.fromAngles("wawwwawawwqqqwwaqww", HexDir.EAST),
        OpApplyForceToPos(true)
    )}
    val APPLY_FORCE_VARIANT_POS = REGISTRY.register("force_variant_pos") { ActionRegistryEntry(
        HexPattern.fromAngles("wawwwawawwqqqwwaqqdq", HexDir.EAST),
        OpApplyForceToPos(false)
    )}

    val ZONE_SHIPS = REGISTRY.register("zone_ships") { ActionRegistryEntry(
        HexPattern.fromAngles("wawwwaqaweeee", HexDir.EAST),
        OpZoneShips
    )}

    fun register() {
        REGISTRY.register()
    }
}