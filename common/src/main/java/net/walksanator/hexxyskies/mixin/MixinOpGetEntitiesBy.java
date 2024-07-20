package net.walksanator.hexxyskies.mixin;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.common.casting.actions.selectors.OpGetEntitiesBy;
import com.llamalad7.mixinextras.sugar.Local;
import kotlin.collections.CollectionsKt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.walksanator.hexxyskies.util.pseudomixin.PMMixinOpGetEntitiesBy;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Comparator;
import java.util.List;

@Mixin(OpGetEntitiesBy.class)
public class MixinOpGetEntitiesBy {

    @Redirect(method = "execute", at = @At(value = "INVOKE", target = "Lkotlin/collections/CollectionsKt;sortedWith(Ljava/lang/Iterable;Ljava/util/Comparator;)Ljava/util/List;"),remap = false)
    List<Entity> sortByWorldDistance(Iterable<Entity> iterable, Comparator<Entity> comparator, @Local Vec3 pos, @Local(argsOnly = true) CastingEnvironment env) {
        return PMMixinOpGetEntitiesBy.resort(iterable,env.getWorld(),pos);
    }
}
