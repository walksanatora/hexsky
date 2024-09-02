package net.walksanator.hexxyskies.mixin;

import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes;
import net.minecraft.resources.ResourceLocation;
import net.walksanator.hexxyskies.HexSkyCommon;
import net.walksanator.hexxyskies.casting.iotas.IotaRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(HexIotaTypes.class)
public class MixinInjectIotaRegistration {
    @Inject(method = "registerTypes", at = @At("RETURN"), remap = false)
    private static void injectRegistration(BiConsumer<IotaType<?>, ResourceLocation> r, CallbackInfo ci) {
        HexSkyCommon.LOGGER.warning("REGISTERING IOTAS REFLECTIVELY");
        IotaRegistry.INSTANCE.register(r);
    }
}
