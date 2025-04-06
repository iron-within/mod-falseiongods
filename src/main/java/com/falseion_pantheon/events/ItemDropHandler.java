package com.falseion_pantheon.events;

import com.falseion_pantheon.FalseionItems;
import com.falseion_pantheon.config.ModConfig;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "falseion_pantheon")
public class ItemDropHandler {
    @SubscribeEvent
    public static void onLivingDrops(ItemTossEvent event) {
        if(event.isCancelable() && event.getEntity().getItem().getItem() == FalseionItems.FRATRICIDE.get()) {
            ItemStack stack = event.getEntity().getItem();
            event.setCanceled(true);

            Inventory playerInventory = event.getPlayer().getInventory();

            if(!playerInventory.contains(FalseionItems.FRATRICIDE.get().getDefaultInstance())) {
                playerInventory.add(FalseionItems.FRATRICIDE.get().getDefaultInstance());
            }
        }
    }
}
