package net.walksanator.hexxyskies.casting.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getBlockPos
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.misc.MediaConstants
import net.minecraft.world.level.block.Block

object OpSwapBlock : ConstMediaAction {
    override val argc: Int
        get() = 2
    override val mediaCost: Long
        get() = MediaConstants.CRYSTAL_UNIT * 5

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val a = args.getBlockPos(0,argc)
        val b = args.getBlockPos(1,argc)
        val a_state = env.world.getBlockState(a)
        val b_state = env.world.getBlockState(b)
        env.world.setBlock(b,a_state, Block.UPDATE_ALL)
        env.world.setBlock(a,b_state, Block.UPDATE_ALL)
        return listOf()
    }
}