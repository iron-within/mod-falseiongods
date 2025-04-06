package com.falseion_pantheon.mixin;

import com.falseion_pantheon.FalseionItems;
import com.falseion_pantheon.config.ModConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractContainerMenu.class)
public abstract class QuickMoveStackMixin {
    @SuppressWarnings("all")
    @Inject(method = "quickMoveStack", at = @At("HEAD"), cancellable = true)
    private void onQuickMoveStack(Player player, int index, CallbackInfoReturnable<ItemStack> cir) {
        if (!ModConfig.VALUES.banFratricideItemMovement.get()) return;
        AbstractContainerMenu menu = (AbstractContainerMenu)(Object)this;
        if (index < 0 || index >= menu.slots.size()) return;
        Slot slot = menu.slots.get(index);
        ItemStack stack = slot.getItem();
        if (stack.getItem() == FalseionItems.FRATRICIDE.get()) {
            // Cancel shift-click transfer
            cir.setReturnValue(ItemStack.EMPTY);
        }
    }
}