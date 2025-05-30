package com.falseion_pantheon.events;

import com.falseion_pantheon.FalseionDamageTypes;
import com.falseion_pantheon.config.ModConfig;
import com.falseion_pantheon.FalseionItems;
import com.falseion_pantheon.data.FalseionSavedData;
import com.falseion_pantheon.registries.EffectsRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.UUID;

public class KillEventHandler {

    // Track last kill timestamps for each player.
    private final HashMap<UUID, Long> lastKillTime = new HashMap<>();

    // When an entity is killed by the player with FRATRICIDE,
    // reset the timer and clear the debuff.
    @SubscribeEvent
    public void onEntityKilled(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            if (player.getMainHandItem().getItem() == FalseionItems.FRATRICIDE.get()) {
                ServerLevel level = (ServerLevel) player.level;
                FalseionSavedData.get(level).setLastKill(player.getUUID(), System.currentTimeMillis());
                player.removeEffect(EffectsRegistry.KHORNE_BERSERK_RAGE.get());
            }
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (isDesignatedBearer(player)) {
            // If player is not under "Khorne's berserker rage", set time
            if(!player.hasEffect(EffectsRegistry.KHORNE_BERSERK_RAGE.get())) {
                ServerLevel level = (ServerLevel) player.level;

                long currentTime = System.currentTimeMillis();
                long lastKill = FalseionSavedData.get(level).getLastKill(player.getUUID(), currentTime);

                // prevent user from resetting buff
                if(currentTime - lastKill < 20) {
                    FalseionSavedData.get(level).setLastKill(player.getUUID(), System.currentTimeMillis());
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntitySpawned(LivingSpawnEvent event) {
//        if(event.getEntity().getName() == '');
    }

// Check the timer and apply the debuff if no kill has been made in the interval.
//    @SubscribeEvent
//    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
//        Player player = event.player;
//        // Ensure player has the cursed item.
//        if (!player.getInventory().contains(new net.minecraft.world.item.ItemStack(FalseionItems.FRATRICIDE.get()))) return;
//
//        UUID playerId = player.getUUID();
//        long currentTime = System.currentTimeMillis();
//        long lastKill = lastKillTime.getOrDefault(playerId, currentTime);
//        long interval = ModConfig.VALUES.fratricideKillInterval.get() * 1000L;
//
//        if (currentTime - lastKill >= interval && !player.hasEffect(com.falseion_pantheon.registries.EffectsRegistry.KHORNE_BERSERK_RAGE.get())) {
//            // Apply debuff "Khorne's berserk rage"
//            // Duration is set high (e.g., 1 hour in ticks) since it only clears on kill.
//            // The amplifier for speed (1 for Speed II) is set here.
//            MobEffectInstance effectInstance = new MobEffectInstance(com.falseion_pantheon.registries.EffectsRegistry.KHORNE_BERSERK_RAGE.get(), 20 * 86400, 0, false, true);
//            player.addEffect(effectInstance);
//        }
//    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (!player.getInventory().contains(new ItemStack(FalseionItems.FRATRICIDE.get()))) return;
        if (!(player.level instanceof ServerLevel serverLevel)) return;

        long currentTime = System.currentTimeMillis();
        long lastKill = FalseionSavedData.get(serverLevel).getLastKill(player.getUUID(), currentTime);
        long interval = ModConfig.VALUES.fratricideKillInterval.get() * 1000L;

        if (currentTime - lastKill >= interval &&
                !player.hasEffect(EffectsRegistry.KHORNE_BERSERK_RAGE.get())) {

            MobEffectInstance effectInstance = new MobEffectInstance(EffectsRegistry.KHORNE_BERSERK_RAGE.get(), 20 * 86400, 0, false, true);
            player.addEffect(effectInstance);

//            NetworkHandler.sendToTrackingPlayers(new StartMusicPacket(player.getId()), player);

//            player.level.playSound(player, player.blockPosition(), FalseionSounds.BERSERKER_RAGE.get(), SoundSource.MUSIC, 1.0f, 0.0f);
//            player.level.playLocalSound(
//                    player.getBlockX(),
//                    player.getBlockY(),
//                    player.getBlockZ(),
//                    FalseionSounds.BERSERKER_RAGE.get(), SoundSource.MUSIC, 1.0f, 0.0f, false);
        }
    }

    // Cancel damage if the player has the immortality component of the debuff.
    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player _player) {
            if (_player.hasEffect(EffectsRegistry.KHORNE_BERSERK_RAGE.get()) &&
                    ModConfig.VALUES.fratricideRageImmortal.get()) {
                event.setCanceled(true);
            }
        }
            
        // Check if the damage source is a player.
        if (event.getSource().getEntity() instanceof Player player) {
            // Check if the player is holding FRATRICIDE in their main hand.
            if (player.getMainHandItem().getItem() == FalseionItems.FRATRICIDE.get()) {
                LivingEntity target = event.getEntity();
                // Force the entity to die, simulating the /kill command.
                target.hurt(FalseionDamageTypes.FRATRICIDE_DAMAGE, Float.MAX_VALUE);
                // Cancel any further damage processing.
                event.setCanceled(true);

//                NetworkHandler.sendToTrackingPlayers(new StopMusicPacket(player.getId()), player);
            }
        }

        // If the entity taking damage is a player and has our debuff, cancel the damage.
        if (event.getEntity() instanceof Player player) {
            if (player.hasEffect(EffectsRegistry.KHORNE_BERSERK_RAGE.get())) {
                // Cancel all damage to simulate immortality.
                event.setCanceled(true);
            }
        }
    }

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
}
