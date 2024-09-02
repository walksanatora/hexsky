package net.walksanator.hexxyskies.mixin;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes;
import net.minecraft.resources.ResourceLocation;
import net.walksanator.hexxyskies.HexSkyCommon;
import net.walksanator.hexxyskies.casting.iotas.IotaRegistry;
import net.walksanator.hexxyskies.casting.patterns.PatternRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(HexActions.class)
public class MixinInjectPatternRegistration {
    @Inject(method = "register", at = @At("RETURN"), remap = false)
    private static void injectRegistration(BiConsumer<ActionRegistryEntry, ResourceLocation> r, CallbackInfo ci) {
        HexSkyCommon.LOGGER.warning("REGISTERING PATTERNS REFLECTIVELY");
        PatternRegistry.INSTANCE.register(r);
    }
}
