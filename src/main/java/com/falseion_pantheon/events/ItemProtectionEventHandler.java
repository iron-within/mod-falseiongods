package com.falseion_pantheon.events;

import com.falseion_pantheon.FalseionItems;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.inventory.AbstractContainerMenu;

@Mod.EventBusSubscriber(modid = "falseion_pantheon")
public class ItemProtectionEventHandler {

    // 1. Remove FratricideItem from drops when the player dies.
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        event.getDrops().removeIf(entityItem -> {
            ItemStack stack = entityItem.getItem();
            return stack.getItem() == FalseionItems.FRATRICIDE.get();
        });
    }

    // 2. When a player respawns, if they don't have FratricideItem, give them one.
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        boolean hasFratricide = false;
        for (ItemStack stack : player.getInventory().items) {
            if (stack.getItem() == FalseionItems.FRATRICIDE.get()) {
                hasFratricide = true;
                break;
            }
        }
        if (!hasFratricide) {
            player.getInventory().add(new ItemStack(FalseionItems.FRATRICIDE.get()));
        }
    }
}
