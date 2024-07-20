package net.walksanator.hexxyskies.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.walksanator.hexxyskies.HexSkyCommon;

@Mod(HexSkyCommon.MOD_ID)
public final class ExampleModForge {
    public ExampleModForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(HexSkyCommon.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        HexSkyCommon.init();
    }
}
