package net.walksanator.hexxyskies.duck;


import org.jetbrains.annotations.Nullable;
import org.valkyrienskies.core.api.ships.ServerShip;

/**
 * this is to be implemented on {@link at.petrak.hexcasting.api.casting.eval.CastingEnvironment}s either directly or via mixin
 * this allows world to ship ambit to work
 */
public interface ShipGetterEnvironment {
    /**
     * gets the ship the env is apart of. should return null if not on a ship
     * @return the ship
     */
    @Nullable
    ServerShip hexsky$getShip();
}
