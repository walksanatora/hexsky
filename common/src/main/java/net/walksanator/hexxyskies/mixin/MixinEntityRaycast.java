package net.walksanator.hexxyskies.mixin;

import at.petrak.hexcasting.common.casting.actions.raycast.OpEntityRaycast;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.walksanator.hexxyskies.util.EntityClipVSKt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Predicate;

@Mixin(OpEntityRaycast.class)
public class MixinEntityRaycast {

    @WrapOperation(method = "execute", at = @At(value = "INVOKE", target = "Lat/petrak/hexcasting/common/casting/actions/raycast/OpEntityRaycast;getEntityHitResult(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;D)Lnet/minecraft/world/phys/EntityHitResult;"))
    EntityHitResult wrapEntityClip(OpEntityRaycast instance, Entity entity, Level level, Vec3 startPos,
                                   Vec3 endPos, AABB aabb, Predicate<Entity> isValid, double maxSqr,
                                   Operation<EntityHitResult> original) {
        return EntityClipVSKt.entityClipIncludeShips(level,entity,startPos,endPos,isValid,maxSqr);
    }
}
