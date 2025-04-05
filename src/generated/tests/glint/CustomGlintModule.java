package tests.render.glint;

import tests.render.GlintRenderTypes;
import tests.base.RuneColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;

/**
 * Module to manage custom glint colors.
 */
public class CustomGlintModule {
    // ThreadLocal to hold the target glint color during rendering.
    private static final ThreadLocal<RuneColor> targetColor = new ThreadLocal<>();

    /**
     * Sets the target color directly. In our case, if the item is Fratricide,
     * we set it to RuneColor.RED. Otherwise, we clear the target.
     *
     * @param stack The item stack.
     */
    public static void updateTargetColor(ItemStack stack) {
        // Check if the item is Fratricide (you can check by comparing instances)
        if (stack.getItem() == com.falseion_pantheon.FalseionItems.FRATRICIDE.get()) {
            setTargetColor(RuneColor.RED); // hard-code custom glint color for Fratricide
        } else {
            setTargetColor(RuneColor.BLANK);
        }
    }

    public static void setTargetColor(RuneColor color) {
        targetColor.set(color);
    }

    public static RuneColor getTargetColor() {
        return targetColor.get();
    }

    public static class Client {
        public static net.minecraft.client.renderer.RenderType getGlint() {
            return renderType(GlintRenderTypes.glint, net.minecraft.client.renderer.RenderType::glint);
        }

        public static RenderType getGlintTranslucent() {
            return renderType(GlintRenderTypes.glintTranslucent, RenderType::glintTranslucent);
        }

        public static RenderType getEntityGlint() {
            return renderType(GlintRenderTypes.entityGlint, RenderType::entityGlint);
        }

        public static RenderType getGlintDirect() {
            return renderType(GlintRenderTypes.glintDirect, RenderType::glintDirect);
        }

        public static RenderType getEntityGlintDirect() {
            return renderType(GlintRenderTypes.entityGlintDirect, RenderType::entityGlintDirect);
        }

        public static RenderType getArmorGlint() {
            return renderType(GlintRenderTypes.armorGlint, RenderType::armorGlint);
        }

        public static RenderType getArmorEntityGlint() {
            return renderType(GlintRenderTypes.armorEntityGlint, RenderType::armorEntityGlint);
        }

        private static net.minecraft.client.renderer.RenderType renderType(
                java.util.Map<RuneColor, net.minecraft.client.renderer.RenderType> customMap,
                java.util.function.Supplier<net.minecraft.client.renderer.RenderType> vanilla) {
            RuneColor color = getTargetColor();
            return color != null ? customMap.get(color) : vanilla.get();
        }
    }
}
