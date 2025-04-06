package com.falseion_pantheon.glint;

import com.falseion_pantheon.FalseionItems;
import net.minecraft.world.item.ItemStack;

public class CustomGlintModule {
    private static final ThreadLocal<Boolean> useCustomGlint = ThreadLocal.withInitial(() -> false);

    public static void updateTargetColor(ItemStack stack) {
        useCustomGlint.set(stack.getItem() == FalseionItems.FRATRICIDE.get());
    }

    public static boolean shouldUseCustomGlint() {
        return useCustomGlint.get();
    }

    public static void reset() {
        useCustomGlint.set(false);
    }
}
