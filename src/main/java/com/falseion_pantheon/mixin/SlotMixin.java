package com.falseion_pantheon.mixin;

import com.falseion_pantheon.FalseionItems;
import com.falseion_pantheon.config.ModConfig;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public abstract class SlotMixin {
    @Shadow
    public AbstractContainerMenu container;

//    @Inject(method = "mayPlace", at = @At("HEAD"), cancellable = true)
//    private void onMayPlace(ItemStack p_40231_, CallbackInfoReturnable<Boolean> cir) {
//        // If the item being placed is our FratricideItem
//        if (p_40231_.getItem() == FalseionItems.FRATRICIDE.get()) {
//            // ...and this slot belongs to a chest container, disallow it.
//            if (container instanceof ChestMenu || container instanceof HopperMenu) {
//                cir.setReturnValue(false);
//            }
//        }
//    }
    @Inject(method = "mayPlace", at = @At("HEAD"), cancellable = true)
    private void onMayPlace(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (!ModConfig.VALUES.banFratricideItemMovement.get()) return;
        if (stack.getItem() == FalseionItems.FRATRICIDE.get()) {
            // Allow placement only if the container is the player's own inventory.
            if (!(container instanceof InventoryMenu)) {
                cir.setReturnValue(false);
            }
        }
    }
}