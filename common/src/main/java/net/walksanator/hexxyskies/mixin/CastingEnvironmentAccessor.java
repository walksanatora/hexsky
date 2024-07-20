package net.walksanator.hexxyskies.mixin;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CastingEnvironment.class)
public interface CastingEnvironmentAccessor {
    @Invoker("isVecInRangeEnvironment")
    boolean invokeIsVecInRangeEnvironment(Vec3 vec);
}
