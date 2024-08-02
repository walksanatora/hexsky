package net.walksanator.hexxyskies;

//import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
//import net.minecraft.util.math.BlockPos;
//import org.valkyrienskies.mod.common.VSGameUtilsKt;


import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import net.walksanator.hexxyskies.casting.AmbitViaRemap;


public final class HexSkyCommon {
    public static final String MOD_ID = "hexsky";

    public static void init() {
        // Write common init code here.

        CastingEnvironment.addCreateEventListener((env) -> {
            env.addExtension(new AmbitViaRemap(env));
        });
        killMe(); //lets go gambling!
        //can_start_enlighten per_world_pattern requires_enlightenment
    }
    public static void killMe() {
        //IotaRegistry.INSTANCE.register(); //moved to a mixin
        //PatternRegistry.INSTANCE.register(); //moved to a mixin
    }
}