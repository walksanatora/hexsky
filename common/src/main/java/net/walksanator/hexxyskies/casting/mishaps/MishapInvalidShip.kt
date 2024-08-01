package net.walksanator.hexxyskies.casting.mishaps

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.Mishap
import at.petrak.hexcasting.api.pigment.FrozenPigment
import at.petrak.hexcasting.common.lib.HexItems
import net.minecraft.Util
import net.minecraft.network.chat.Component
import net.minecraft.world.item.DyeColor

class MishapInvalidShip(val specific: Any) : Mishap() {
    override fun accentColor(ctx: CastingEnvironment, errorCtx: Context): FrozenPigment = FrozenPigment(HexItems.DYE_PIGMENTS[DyeColor.YELLOW]!!
        .defaultInstance, ctx.castingEntity?.uuid?: Util.NIL_UUID)

    override fun errorMessage(ctx: CastingEnvironment, errorCtx: Context): Component? = Component.translatable("hexsky.mishap.invalid", specific)

    override fun execute(env: CastingEnvironment, errorCtx: Context, stack: MutableList<Iota>) {}
}