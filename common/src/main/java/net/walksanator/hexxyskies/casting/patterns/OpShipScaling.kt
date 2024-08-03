package net.walksanator.hexxyskies.casting.patterns

import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getDoubleBetween
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import net.walksanator.hexxyskies.getShip
import org.valkyrienskies.core.api.ships.ServerShip
import org.valkyrienskies.core.apigame.world.ServerShipWorldCore
import org.valkyrienskies.core.impl.game.ShipTeleportDataImpl
import org.valkyrienskies.mod.common.shipObjectWorld
import org.valkyrienskies.mod.common.util.toMinecraft
import org.valkyrienskies.mod.common.vsPipeline
import kotlin.math.abs

class OpShipScaling : SpellAction {
    override val argc: Int
        get() = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val ship = args.getShip(0, env.world, argc)
        val scale = args.getDoubleBetween(1, 0.25, 4.0, argc)

        return SpellAction.Result(Spell(ship, scale), scale.toLong(), listOf())
    }

    private class Spell(private val ship: ServerShip, private val scale: Double): RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            env.world.shipObjectWorld.teleportShip(ship, ShipTeleportDataImpl(newScale = scale))
        }
    }
}