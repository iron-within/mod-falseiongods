package com.falseion_pantheon;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FalseionCreativeTab extends CreativeModeTab {
    public static final FalseionCreativeTab instance = new FalseionCreativeTab(CreativeModeTab.TABS.length, FalseionCredentials.Id);

    private FalseionCreativeTab(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return new ItemStack(FalseionItems.KHORNE_ICON.get());
    }
}
