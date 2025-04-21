package com.falseion_pantheon.mixin;

import com.falseion_pantheon.Falseion;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public class FogMixin {
//    @Inject(
//            method = "setupFog",
//            at = @At("HEAD"),
//            cancellable = true
//    )
//    /**
//     * @Camera p_234173_
//     * @FogRenderer.FogMode p_234174_
//     * @Float p_234175_ render distance
//     * @Boolean p_234176_ thickFog
//     * @Float p_234177_ partial ticks
//     * @CallbackInfo ci
//     */
//    private static void forceFog(
//            Camera p_234173_, FogRenderer.FogMode p_234174_, float p_234175_, boolean p_234176_, float p_234177_, CallbackInfo ci
//    ) {
//        FogRenderer.setupFog(p_234173_, p_234174_, 10, true, p_234177_);
//        ci.cancel(); // Prevent other mods/vanilla from overriding
//    }

    @Inject(
            method = "setupFog(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/FogRenderer$FogMode;FZF)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void forceFog(
            Camera camera, // Camera object (replaces Entity)
            FogRenderer.FogMode fogMode, // Fog type (e.g., FOG_SKY, FOG_TERRAIN)
            float renderDistance, // Current render distance
            boolean thickFog, // Thick fog state (e.g., in Nether)
            float partialTicks, // Partial tick time
            CallbackInfo ci
    ) {
        Falseion.LOGGER.info("Force-fog of custom render distance and type.");
        // Force fog parameters
        RenderSystem.setShaderFogStart(0.0f); // Start fog immediately
        RenderSystem.setShaderFogEnd(12.0f); // Set fog end distance (adjust as needed)

        // Cancel vanilla logic to enforce your fog
        ci.cancel();
    }
}
