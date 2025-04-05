package com.falseion_pantheon.events;

import com.falseion_pantheon.config.ModConfig;
import com.falseion_pantheon.FalseionItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class InventoryListener {

    // Checks if the player is the designated bearer.
    // If the designated name is empty, the condition is ignored.
    private boolean isDesignatedBearer(Player player) {
        String designatedName = ModConfig.VALUES.fratricideDesignatedBearer.get();
        if (designatedName == null || designatedName.trim().isEmpty()) {
            // No designated bearer set; ignore condition.
            return true;
        }
        // Compare the player's name with the designated name (ignoring case).
        return player.getName().getString().equalsIgnoreCase(designatedName);
    }

    // When the player logs in, ensure they receive the FRATRICIDE item if they are the designated bearer.
    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (isDesignatedBearer(player)) {
            // Check if the player already has the FRATRICIDE item.
            boolean hasItem = player.getInventory().items.stream()
                    .anyMatch(stack -> stack.getItem() == FalseionItems.FRATRICIDE.get());
            if (!hasItem) {
                // Give the player one FRATRICIDE item.
                ItemStack cursedItem = new ItemStack(FalseionItems.FRATRICIDE.get());
                player.getInventory().add(cursedItem);
            }
        }
    }

    // When a player picks up an item, remove FRATRICIDE if the player isn't the designated bearer.
    @SubscribeEvent
    public void onInventoryChanged(PlayerEvent.ItemPickupEvent event) {
        Player player = event.getEntity();
        ItemStack pickedUp = event.getStack();
        if (pickedUp.getItem() == FalseionItems.FRATRICIDE.get() && !isDesignatedBearer(player)) {
            // Remove the item by reducing its stack size.
            pickedUp.shrink(pickedUp.getCount());
        }
    }

    // Optionally, an additional safeguard on every inventory tick.
    // This ensures that if a non-designated bearer somehow has the FRATRICIDE item, it gets removed.
    @SubscribeEvent
    public void onPlayerTick(net.minecraftforge.event.TickEvent.PlayerTickEvent event) {
        // Only proceed on server side to enforce logic
        Player player = event.player;
        // Check if the player is the designated bearer, using your isDesignatedBearer() logic.
        if (!isDesignatedBearer(player)) {
            // Remove FRATRICIDE from the player's inventory
            player.getInventory().items.stream()
                    .filter(stack -> stack.getItem() == FalseionItems.FRATRICIDE.get())
                    .forEach(stack -> stack.setCount(0));
        }
    }
}
