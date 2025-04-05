package com.falseion_pantheon.items.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FratricideItem extends Item {

    public FratricideItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1; // Non-stackable.
    }

    @Override
    public boolean isFoil(@NotNull ItemStack p_41453_) {
        return true;
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        // Prevent placement as a block.
        return InteractionResult.FAIL;
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        // Use a translation key for localization.
        return Component.translatable("item.falseion_pantheon.fratricide.name");
    }

    public static Component getStaticComponent() {
        return Component.translatable("item.falseion_pantheon.fratricide.name");
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack,
                                @Nullable Level level,
                                List<Component> tooltip,
                                @NotNull TooltipFlag flag) {
        // Use a translation key for the tooltip.
        tooltip.add(Component.translatable("item.falseion_pantheon.fratricide.tooltip"));
    }
}
