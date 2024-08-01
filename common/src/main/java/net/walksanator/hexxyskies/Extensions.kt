package net.walksanator.hexxyskies

import at.petrak.hexcasting.api.casting.iota.*
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.walksanator.hexxyskies.casting.iotas.ShipIota
import net.walksanator.hexxyskies.casting.mishaps.MishapInvalidShip
import org.valkyrienskies.core.api.ships.ServerShip
import org.valkyrienskies.core.api.ships.Ship
import org.valkyrienskies.core.api.ships.getAttachment
import org.valkyrienskies.core.api.ships.properties.ShipInertiaData
import org.valkyrienskies.core.impl.game.ships.ShipData
import org.valkyrienskies.core.impl.game.ships.ShipObjectServer
import org.valkyrienskies.mod.common.util.GameTickForceApplier

fun List<Iota>.getShip(idx: Int, world: ServerLevel, argc: Int = 0): ServerShip {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (x is ShipIota) {
        return x.getShip(world) ?:
            throw MishapInvalidShip(Component.translatable("hexsky.mishap.invalid.reason.null"))
    }

    throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "ship")
}

fun Ship.getInertialData(): ShipInertiaData? {
    return (this as? ServerShip)?.inertiaData
}


//neat idea but NO
//val ANY_TO_IOTA_MAP: Map<Class<*>, (Any) -> Iota> = mutableMapOf(
//    Double::class.java to {DoubleIota(it as Double)},
//    Vec3::class.java to {Vec3Iota(it as Vec3)},
//    Boolean::class.java to {BooleanIota(it as Boolean)},
//    HexPattern::class.java to {PatternIota(it as HexPattern)}
//)
//
//fun Any?.toIota(): Iota {
//    return when (this) {
//        null -> NullIota()
//        ANY_TO_IOTA_MAP[this::class.java] -> ANY_TO_IOTA_MAP[this::class.java]!!.invoke(this)
//        else -> throw IllegalArgumentException("class %s does not appear to have any sort of mapping to iota".format(this::class.java.name))
//    }
//}

