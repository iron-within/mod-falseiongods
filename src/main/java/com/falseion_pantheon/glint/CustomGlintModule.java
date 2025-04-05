package com.falseion_pantheon.glint;

import com.falseion_pantheon.FalseionItems;
import net.minecraft.world.item.ItemStack;

public class CustomGlintModule {
    public static final ThreadLocal<Boolean> shouldUseRedGlint = ThreadLocal.withInitial(() -> false);

    public static void updateTargetColor(ItemStack stack) {
        shouldUseRedGlint.set(stack.getItem() == FalseionItems.FRATRICIDE.get());
    }
}
