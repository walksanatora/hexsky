package net.walksanator.hexxyskies.casting.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getBlockPos
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.misc.MediaConstants
import net.minecraft.server.level.ServerLevel
import net.walksanator.hexxyskies.casting.iotas.ShipIota
import net.walksanator.hexxyskies.ship.getShipDataHolder
import org.valkyrienskies.core.api.ships.ServerShip
import org.valkyrienskies.mod.common.getShipManagingPos
import org.valkyrienskies.mod.common.getShipObjectManagingPos
import org.valkyrienskies.mod.common.shipObjectWorld

object OpPosToShip : ConstMediaAction {
    override val argc: Int = 1
    override val mediaCost: Long = MediaConstants.DUST_UNIT
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val pos = args.getBlockPos(0, argc)
        env.assertPosInRange(pos)
        val ship = env.world.getShipObjectManagingPos(pos)?.let {
            if (it.getShipDataHolder().cloaked) {null} else {ShipIota(it.id, it.slug)}
        }?: NullIota()
        return listOf(ship)
    }
}
