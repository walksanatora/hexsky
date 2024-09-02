package net.walksanator.hexxyskies.casting.patterns

import at.petrak.hexcasting.api.casting.ActionRegistryEntry
import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import dev.architectury.platform.Platform
import net.minecraft.resources.ResourceLocation
import net.walksanator.hexxyskies.HexSkyCommon
import java.util.function.BiConsumer
import java.util.function.Supplier

object PatternRegistry {
    private val REGISTRY = mutableMapOf<ResourceLocation,ActionRegistryEntry>()

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

    val ASSEMBLE_SHIP = REGISTRY.register("assemble") { ActionRegistryEntry(
        HexPattern.fromAngles("wawwdeeeeeqa", HexDir.EAST),
        OpAssemble
    )}

    val SHIP_TO_WORLD_POS = REGISTRY.register("shiptoworldpos") { ActionRegistryEntry(
        HexPattern.fromAngles("wawwwaawawwawwqqwq", HexDir.EAST),
        OpVec2World
    )}

    val SWAP_BLOCKS  = REGISTRY.register("swap") { ActionRegistryEntry(
        HexPattern.fromAngles("qaqqqqqewqaqqqqqewqaqqqqqewqaqqqqqewqaqqqqqewqaqqqqqe", HexDir.SOUTH_EAST),
        OpSwapBlock
    )}

    val SHIP_SCALE = REGISTRY.register("ship_scale") { ActionRegistryEntry(
        HexPattern.fromAngles("wawwa", HexDir.EAST),
        OpShipScale
    )}

    val SHIP_SCALING = REGISTRY.register("ship_scaling") { ActionRegistryEntry(
        HexPattern.fromAngles("awwwaa", HexDir.EAST),
        OpShipScaling()
    )}

    val SHIP_RAYCAST = REGISTRY.register("ship_raycast") { ActionRegistryEntry(
        HexPattern.fromAngles("wawwwaddwaa", HexDir.EAST),
        OpShipRaycast
    )}

    val BLOCK_MASS = REGISTRY.register("block_mass") { ActionRegistryEntry(
        HexPattern.fromAngles("wawwwqaqdeee", HexDir.EAST),
        OpBlockMass
    )}

    //#region hexal interop
    val EMBARK = if (Platform.isModLoaded("hexal")) {
        REGISTRY.register("embark") { ActionRegistryEntry(
            HexPattern.fromAngles("wawwwdewdwewd", HexDir.EAST),
            OpEmbark
        )}
    } else {null}
    val DISEMBARK = if (Platform.isModLoaded("hexal")) {
        REGISTRY.register("disembark") { ActionRegistryEntry(
            HexPattern.fromAngles("wawwwqawqwawq", HexDir.EAST),
            OpDisembark
        )}
    } else {null}
    //#endregion

    //#region moreiotas interop
    val SHIP_SLUG_GET = if (Platform.isModLoaded("moreiotas")) {
        REGISTRY.register("ship_slug_get") { ActionRegistryEntry(
            HexPattern.fromAngles("wawwwaqwa", HexDir.EAST),
            OpShipGetSlug
        )}
    } else {null}
    val SHIP_SLUG_SET = if (Platform.isModLoaded("moreiotas")) {
        REGISTRY.register("ship_slug_set") { ActionRegistryEntry(
            HexPattern.fromAngles("wawadwedw", HexDir.EAST),
            OpShipSetSlug
        )}
    } else {null}
    val WORLD_TO_SHIP_MATRIX = if (Platform.isModLoaded("moreiotas")) {
        REGISTRY.register("world_to_ship_matrix") { ActionRegistryEntry(
            HexPattern.fromAngles("wawwwaeawae", HexDir.EAST),
            OpWorldToShipMatrix
        )}
    } else {null}
    val SHIP_TO_WORLD_MATRIX = if (Platform.isModLoaded("moreiotas")) {
        REGISTRY.register("ship_to_world_matrix") { ActionRegistryEntry(
            HexPattern.fromAngles("wawqqdwdqdw", HexDir.EAST),
            OpShipToWorldMatrix
        )}} else {null}
    //#endregion

    //#region complexhex interop
    val SHIP_ROT_QUAT = if (Platform.isModLoaded("complexhex")) {
        REGISTRY.register("ship_rot_quat") { ActionRegistryEntry(
            HexPattern.fromAngles("wawwwaawe", HexDir.EAST),
            OpShipRotQuat
        )}
    } else {null}
    val SHIP_ROT_QUAT_SET = if (Platform.isModLoaded("complexhex")) {
        REGISTRY.register("ship_rot_quat_set") { ActionRegistryEntry(
            HexPattern.fromAngles("wawwqddqe", HexDir.EAST),
            OpShipSetRotQuat
        )}
    } else {null}
    //#endregion

    fun register(consumer: BiConsumer<ActionRegistryEntry, ResourceLocation>) {
        HexSkyCommon.LOGGER.warning("injecting patterns into hexcasting's loading")
        REGISTRY.forEach {
            HexSkyCommon.LOGGER.warning(it.key.toString())
            consumer.accept(it.value, it.key)
        }
    }
}

fun <T> MutableMap<ResourceLocation,T>.register(id: String, value: Supplier<T>) {
    this[ResourceLocation(HexSkyCommon.MOD_ID,id)] = value.get()
}