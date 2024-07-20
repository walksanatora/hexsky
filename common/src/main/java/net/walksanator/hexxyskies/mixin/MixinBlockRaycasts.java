package net.walksanator.hexxyskies.mixin;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.common.casting.actions.raycast.OpBlockAxisRaycast;
import at.petrak.hexcasting.common.casting.actions.raycast.OpBlockRaycast;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.valkyrienskies.mod.common.world.RaycastUtilsKt;

@Mixin( { OpBlockRaycast.class, OpBlockAxisRaycast.class })
public class MixinBlockRaycasts {
    @WrapOperation(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;clip(Lnet/minecraft/world/level/ClipContext;)Lnet/minecraft/world/phys/BlockHitResult;"))
    BlockHitResult redirectServerLevelClip(ServerLevel instance, ClipContext clipContext, Operation<BlockHitResult> original, @Local(argsOnly = true) CastingEnvironment env) {
        return RaycastUtilsKt.clipIncludeShips(env.getWorld(), clipContext);
    }
}
