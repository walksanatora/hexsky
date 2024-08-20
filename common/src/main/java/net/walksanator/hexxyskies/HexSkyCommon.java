package net.walksanator.hexxyskies;

//import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
//import net.minecraft.util.math.BlockPos;
//import org.valkyrienskies.mod.common.VSGameUtilsKt;


import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.interop.HexInterop;
import dev.architectury.platform.Platform;
import net.walksanator.hexxyskies.casting.AmbitViaRemap;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.logging.Logger;


public final class HexSkyCommon {
    public static final String MOD_ID = "hexsky";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    public static void init() {
        // Write common init code here.

        CastingEnvironment.addCreateEventListener((env) -> {
            env.addExtension(new AmbitViaRemap(env));
        });
        killMe(); //lets go gambling!
        //can_start_enlighten per_world_pattern requires_enlightenment

        if (Platform.isModLoaded("complexhex") || Platform.isModLoaded("moreiotas") || Platform.isModLoaded("hexal"))
            PatchouliAPI.get().setConfigFlag(HexInterop.PATCHOULI_ANY_INTEROP_FLAG, true);
    }
    public static void killMe() {
        //IotaRegistry.INSTANCE.register(); //moved to a mixin
        //PatternRegistry.INSTANCE.register(); //moved to a mixin
    }
}