package net.walksanator.hexxyskies.casting

import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.OperationResult
import at.petrak.hexcasting.api.casting.eval.sideeffects.OperatorSideEffect
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds

interface VariableMediaAction : Action {
    val argc: Int

    override fun operate(
        env: CastingEnvironment,
        image: CastingImage,
        continuation: SpellContinuation
    ): OperationResult {
        val stack = image.stack.toMutableList()

        if (this.argc > stack.size)
            throw MishapNotEnoughArgs(this.argc, stack.size)
        val args = stack.takeLast(this.argc)
        repeat(this.argc) { stack.removeLast() }
        val result = this.render(args, env)

        val sideEffects = mutableListOf(
            OperatorSideEffect.ConsumeMedia(result.mediaConsumed),
            OperatorSideEffect.AttemptSpell(result)
        )

        val image2 = image.copy(stack = stack)
        return OperationResult(image2, sideEffects, continuation, HexEvalSounds.NORMAL_EXECUTE)
    }

    fun render(args: List<Iota>, env: CastingEnvironment): Result

    abstract class Result(val mediaConsumed: Long = MediaConstants.SHARD_UNIT) : RenderedSpell {
        abstract fun execute(env: CastingEnvironment): List<Iota>

        override fun cast(env: CastingEnvironment) {
            throw AssertionError("This Should Not be reached!")
        }

        override fun cast(env: CastingEnvironment, image: CastingImage): CastingImage? {
            val stack = image.stack.toMutableList()
            stack.addAll(execute(env))
            return image.copy(stack, opsConsumed = image.opsConsumed + 1)
        }

    }
}