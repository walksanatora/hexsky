package net.walksanator.hexxyskies.mixin.env;

import dan200.computercraft.api.pocket.IPocketAccess;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleSide;
import kotlin.Pair;
import net.minecraft.world.entity.Entity;
import net.walksanator.hexxyskies.duck.ShipGetterEnvironment;
import net.walksantor.hextweaks.casting.environment.ComputerCastingEnv;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

@Pseudo
@Mixin(ComputerCastingEnv.class)
public abstract class DuckShipGetComputerEnv implements ShipGetterEnvironment {
    @Final
    @Shadow(remap = false)
    private IPocketAccess pocketData;

//    @Final
//    @Shadow
//    private Pair<ITurtleAccess, TurtleSide> turtleData;

    @Shadow(remap = false)
    public abstract Pair<ITurtleAccess, TurtleSide> getTurtleData();

    @Override
    public @Nullable ServerShip hexsky$getShip() {
        if (getTurtleData() != null) {
            return (ServerShip) VSGameUtilsKt.getShipManagingPos(getTurtleData().getFirst().getLevel(), getTurtleData().getFirst().getPosition());
        } else {
            Entity entity = pocketData.getEntity();
            return (ServerShip) VSGameUtilsKt.getShipManagingPos(entity.level(), entity.position());
        }
    }
}
