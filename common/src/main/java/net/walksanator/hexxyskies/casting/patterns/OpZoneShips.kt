package net.walksanator.hexxyskies.casting.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getDoubleBetween
import at.petrak.hexcasting.api.casting.getPositiveDouble
import at.petrak.hexcasting.api.casting.getVec3
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.phys.AABB
import net.walksanator.hexxyskies.casting.iotas.ShipIota
import net.walksanator.hexxyskies.ship.getShipDataHolder
import org.valkyrienskies.core.api.ships.ServerShip
import org.valkyrienskies.mod.common.getShipsIntersecting
import org.valkyrienskies.mod.common.shipObjectWorld

object OpZoneShips: ConstMediaAction {
    override val argc: Int
        get() = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val center = args.getVec3(0, argc)
        val radius = args.getDoubleBetween(1, 0.01, 32.0, argc)
        val ships = env.world.getShipsIntersecting(AABB(center, center).inflate(radius)).filter { ship ->
            ship is ServerShip && !ship.loaded(env.world).getShipDataHolder().cloaked
        }.map { ship -> ShipIota(ship.id, ship.slug) }
        return listOf(ListIota(ships))
    }
}

/*
gets the ShipObjectServer variant of said server ship (or the ShipData variant if it is not loaded)
 */
fun ServerShip.loaded(world: ServerLevel): ServerShip {
    val sow = world.shipObjectWorld
    val fly = sow.loadedShips.getById(this.id)?: sow.allShips.getById(this.id)!!
    return fly
}