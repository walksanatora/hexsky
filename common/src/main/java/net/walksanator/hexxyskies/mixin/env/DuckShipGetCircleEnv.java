package net.walksanator.hexxyskies.mixin.env;

import at.petrak.hexcasting.api.casting.circles.BlockEntityAbstractImpetus;
import at.petrak.hexcasting.api.casting.circles.CircleExecutionState;
import at.petrak.hexcasting.api.casting.eval.env.CircleCastEnv;
import at.petrak.hexcasting.api.casting.eval.env.PlayerBasedCastEnv;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.walksanator.hexxyskies.duck.ShipGetterEnvironment;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

@Mixin(CircleCastEnv.class)
public abstract class DuckShipGetCircleEnv implements ShipGetterEnvironment {
    @Final
    @Shadow(remap = false)
    protected CircleExecutionState execState;

    @Shadow(remap = false)
    public abstract BlockEntityAbstractImpetus getImpetus();

    @Override
    public @Nullable ServerShip hexsky$getShip() {
        return (ServerShip)VSGameUtilsKt.getShipManagingPos(getImpetus().getLevel(), execState.impetusPos);
    }
}
