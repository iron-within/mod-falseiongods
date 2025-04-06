package com.falseion_pantheon.mixin;

import com.falseion_pantheon.glint.CustomGlintModule;
import com.falseion_pantheon.items.custom.FratricideItem;
import com.falseion_pantheon.render.types.RedGlintRenderType;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void beforeRender(ItemStack p_115144_, ItemTransforms.TransformType p_115145_, boolean p_115146_, PoseStack p_115147_, MultiBufferSource p_115148_, int p_115149_, int p_115150_, BakedModel p_115151_, CallbackInfo ci) {
        CustomGlintModule.updateTargetColor(p_115144_);
    }

    @SuppressWarnings("all")
    @ModifyExpressionValue(
            method = "getFoilBufferDirect(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/renderer/RenderType;Z)Lcom/mojang/blaze3d/vertex/VertexConsumer;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/RenderType;glintDirect()Lnet/minecraft/client/renderer/RenderType;"
            )
    )
    private static RenderType replaceGlint(RenderType original) {
        if (CustomGlintModule.shouldUseCustomGlint()) {
            return RedGlintRenderType.RED_GLINT;
        }
        return original;
    }

    // getArmorFoilBuffer: used only by the Elytra armor layer //

    //TODO: I think this one is currently unused, ElytraLayer always calls getArmorFoilBuffer with 'false' in the third param
    @ModifyExpressionValue(method = "getArmorFoilBuffer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;armorGlint()Lnet/minecraft/client/renderer/RenderType;"))
    private static RenderType getArmorGlint(RenderType prev) {
        if (CustomGlintModule.shouldUseCustomGlint()) {
            return RedGlintRenderType.RED_GLINT;
        }
        return prev;
    }

    @ModifyExpressionValue(method = "getArmorFoilBuffer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;armorEntityGlint()Lnet/minecraft/client/renderer/RenderType;"))
    private static RenderType getArmorEntityGlint(RenderType prev) {
        if (CustomGlintModule.shouldUseCustomGlint()) {
            return RedGlintRenderType.RED_GLINT;
        }
        return prev;
    }

    // getFoilBuffer: tiny potato, ??? supposedly used for enchanted items in-world but i can //

    @ModifyExpressionValue(method = "getFoilBuffer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;glintTranslucent()Lnet/minecraft/client/renderer/RenderType;"))
    private static RenderType getGlintTranslucent(RenderType prev) {
        if (CustomGlintModule.shouldUseCustomGlint()) {
            return RedGlintRenderType.RED_GLINT;
        }
        return prev;
    }

    @ModifyExpressionValue(method = "getFoilBuffer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;glint()Lnet/minecraft/client/renderer/RenderType;"))
    private static RenderType getGlint(RenderType prev) {
        if (CustomGlintModule.shouldUseCustomGlint()) {
            return RedGlintRenderType.RED_GLINT;
        }
        return prev;
    }

    @ModifyExpressionValue(method = "getFoilBuffer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;entityGlint()Lnet/minecraft/client/renderer/RenderType;"))
    private static RenderType getEntityGlint(RenderType prev) {
        if (CustomGlintModule.shouldUseCustomGlint()) {
            return RedGlintRenderType.RED_GLINT;
        }
        return prev;
    }

    // getFoilBufferDirect: used most of the time for rendering enchanted items. glintDirect is used most often //

    @ModifyExpressionValue(method = "getFoilBufferDirect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;glintDirect()Lnet/minecraft/client/renderer/RenderType;"))
    private static RenderType getGlintDirect(RenderType prev) {
        if (CustomGlintModule.shouldUseCustomGlint()) {
            return RedGlintRenderType.RED_GLINT;
        }
        return prev;
    }

    @ModifyExpressionValue(method = "getFoilBufferDirect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;entityGlintDirect()Lnet/minecraft/client/renderer/RenderType;"))
    private static RenderType getEntityGlintDirect(RenderType prev) {
        if (CustomGlintModule.shouldUseCustomGlint()) {
            return RedGlintRenderType.RED_GLINT;
        }
        return prev;
    }

    // glintDirect() in getFoilBufferDirect
    @SuppressWarnings("all")
    @ModifyExpressionValue(
            method = "getFoilBufferDirect(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/renderer/RenderType;Z)Lcom/mojang/blaze3d/vertex/VertexConsumer;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/RenderType;glintDirect()Lnet/minecraft/client/renderer/RenderType;"
            )
    )
    private static RenderType replaceGlintDirect(RenderType original) {
        if (CustomGlintModule.shouldUseCustomGlint()) {
            return RedGlintRenderType.RED_GLINT;
        }
        return original;
    }

    // entityGlintDirect() in getFoilBufferDirect
    @SuppressWarnings("all")
    @ModifyExpressionValue(
            method = "getFoilBufferDirect(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/renderer/RenderType;Z)Lcom/mojang/blaze3d/vertex/VertexConsumer;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/RenderType;entityGlintDirect()Lnet/minecraft/client/renderer/RenderType;"
            )
    )
    private static RenderType replaceEntityGlintDirect(RenderType original) {
        if (CustomGlintModule.shouldUseCustomGlint()) {
            return RedGlintRenderType.RED_GLINT;
        }
        return original;
    }

    @Unique
    private static ItemStack forge_1_19_2_43_4_6_mdk$tempStack = ItemStack.EMPTY;

    // Capture the currently rendered ItemStack at the start of renderItem.
    @SuppressWarnings("all")
    @Inject(method = "renderItem(Lnet/minecraft/world/item/ItemStack;" +
            "Lnet/minecraft/client/render/item/ItemTransforms$TransformType;" +
            "ZLnet/minecraft/client/util/math/MatrixStack;" +
            "Lnet/minecraft/client/renderer/VertexConsumerProvider;" +
            "II Lnet/minecraft/client/model/GeometryBakedModel;)V",
            at = @At("HEAD"))
    private void onStartRenderItem(ItemStack stack, ItemTransforms.TransformType transformType, boolean leftHanded,
                                   PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay,
                                   BakedModel model, CallbackInfo ci) {
        forge_1_19_2_43_4_6_mdk$tempStack = stack;
    }

    // Clear the temporary stack after rendering.
    @SuppressWarnings("all")
    @Inject(method = "renderItem(Lnet/minecraft/world/item/ItemStack;" +
            "Lnet/minecraft/client/render/item/ItemTransforms$TransformType;" +
            "ZLnet/minecraft/client/util/math/MatrixStack;" +
            "Lnet/minecraft/client/renderer/VertexConsumerProvider;" +
            "II Lnet/minecraft/client/model/GeometryBakedModel;)V",
            at = @At("TAIL"))
    private void onEndRenderItem(ItemStack stack, ItemTransforms.TransformType transformType, boolean leftHanded,
                                 PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay,
                                 BakedModel model, CallbackInfo ci) {
        forge_1_19_2_43_4_6_mdk$tempStack = ItemStack.EMPTY;
    }

    // Modify the glint flag: force glint if the current item is a FratricideItem.
    @SuppressWarnings("all")
    @ModifyVariable(method = "getDirectItemGlintConsumer", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    private static boolean changeGlint(boolean glint) {
        return glint || (forge_1_19_2_43_4_6_mdk$tempStack.getItem() instanceof FratricideItem);
    }

    // Replace the default glint RenderType with our custom red glint for FratricideItem.
    @SuppressWarnings("all")
    @ModifyExpressionValue(method = "getDirectItemGlintConsumer",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;glintDirect()Lnet/minecraft/client/renderer/RenderType;"))
    private static RenderType onGetDirectItemGlintConsumer(RenderType original) {
        if (forge_1_19_2_43_4_6_mdk$tempStack.getItem() instanceof FratricideItem) {
            return RedGlintRenderType.RED_GLINT;
        }
        return original;
    }
}
