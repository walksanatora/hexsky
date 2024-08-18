package net.walksanator.hexxyskies.mixin;

import at.petrak.hexcasting.interop.HexInterop;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.architectury.platform.Platform;
import vazkii.patchouli.api.PatchouliAPI;

@Mixin(HexInterop.class)
public class MixinHexInterop {
    @Inject(
            method = "initPatchouli",
            at = @At("RETURN"),
            remap = false
    )
    private static void hexsky$forceInteropIfModsExist(CallbackInfo ci) {
        if (Platform.isModLoaded("complexhex") ||
                Platform.isModLoaded("moreiotas") ||
                Platform.isModLoaded("hexal")) {
            PatchouliAPI.get().setConfigFlag(HexInterop.PATCHOULI_ANY_INTEROP_FLAG, true);
        }
    }
}
