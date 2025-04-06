package com.falseion_pantheon.mixin;

import com.falseion_pantheon.FalseionItems;
import com.falseion_pantheon.config.ModConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerMenu.class)
public abstract class ContainerClickMixin {

    /**
     * This mixin intercepts container click events to prevent moving FratricideItem from the player's inventory to a container.
     * Adjust the threshold as needed for your container layouts.
     */
    @Inject(method = "clicked", at = @At("HEAD"), cancellable = true)
    private void onClicked(int slotId, int dragType, ClickType clickType, Player player, CallbackInfo ci) {
        if (!ModConfig.VALUES.banFratricideItemMovement.get()) return;
        AbstractContainerMenu menu = (AbstractContainerMenu)(Object)this;
        // Ensure slotId is valid
        if (slotId < 0 || slotId >= menu.slots.size()) return;
        Slot slot = menu.slots.get(slotId);
        ItemStack stack = slot.getItem();
        if (stack.getItem() != FalseionItems.FRATRICIDE.get()) return;

        // Heuristic: in many container GUIs (like chests), the player's inventory is the lower 36 slots.
        // If the container has more than, say, 36 slots and the clicked slot is NOT within the last 36 slots, block the action.
        int totalSlots = menu.slots.size();
        int playerInventoryStart = totalSlots - 36; // adjust if necessary
        if (slotId < playerInventoryStart) {
            ci.cancel();
        }
    }
}
