package net.walksanator.hexxyskies;

//import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
//import net.minecraft.util.math.BlockPos;
//import org.valkyrienskies.mod.common.VSGameUtilsKt;


import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import net.minecraft.core.BlockPos;
import net.walksanator.hexxyskies.casting.AmbitViaRemap;
import net.walksanator.hexxyskies.casting.iotas.IotaRegistry;
import net.walksanator.hexxyskies.casting.patterns.PatternRegistry;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

public final class HexSkyCommon {
    public static final String MOD_ID = "hexsky";

    public static void init() {
        // Write common init code here.

        CastingEnvironment.addCreateEventListener((env) -> {
            env.addExtension(new AmbitViaRemap(env));
        });
        killMe(); //lets go gambling!
    }
    public static void killMe() {
        IotaRegistry.INSTANCE.register();
        PatternRegistry.INSTANCE.register();
    }
}