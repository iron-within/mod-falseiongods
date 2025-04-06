package com.falseion_pantheon.events.server;

import com.falseion_pantheon.FalseionItems;
import com.falseion_pantheon.network.sounds.BerserkerRageSoundPacket;
import com.falseion_pantheon.network.sounds.BerserkerRageSoundPacket.Action;
import com.falseion_pantheon.registries.EffectsRegistry;
import com.falseion_pantheon.network.NetworkHandler; // your network channel
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ServerEffectSoundHandler {

    @SubscribeEvent
    public static void onEffectAdded(MobEffectEvent.Added event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        // Only consider players with the FracticideItem
        if (!player.getInventory().contains(new net.minecraft.world.item.ItemStack(FalseionItems.FRATRICIDE.get()))) return;
        MobEffectInstance effect = event.getEffectInstance();
        if (effect.getEffect() == EffectsRegistry.KHORNE_BERSERK_RAGE.get()) {
            // Send START packet to nearby players (e.g., within 32 blocks)
            NetworkHandler.sendToAllAround(player, new BerserkerRageSoundPacket(player.getUUID(), BerserkerRageSoundPacket.Action.START));
        }
    }

    @SubscribeEvent
    public static void onEffectRemoved(MobEffectEvent.Remove event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        MobEffectInstance effect = event.getEffectInstance();
        assert effect != null;
        if (effect.getEffect() == EffectsRegistry.KHORNE_BERSERK_RAGE.get()) {
            // Send STOP packet to nearby players so they fade out the sound
            NetworkHandler.sendToAllAround(player, new BerserkerRageSoundPacket(player.getUUID(), BerserkerRageSoundPacket.Action.STOP));
        }
    }
}
