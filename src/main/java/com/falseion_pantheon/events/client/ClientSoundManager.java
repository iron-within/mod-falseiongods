package com.falseion_pantheon.events.client;

import com.falseion_pantheon.FalseionSounds;
import com.falseion_pantheon.config.ModConfig;
import com.falseion_pantheon.network.sounds.BerserkerRageSoundPacket;
import com.falseion_pantheon.sound.FadingMusicInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public class ClientSoundManager {
    // Active music instances keyed by source player's UUID.
    private static final Map<UUID, FadingMusicInstance> activeMusic = new HashMap<>();

    public static void handleBerserkerRageSoundPacket(BerserkerRageSoundPacket packet) {
        Minecraft mc = Minecraft.getInstance();
        UUID sourceUUID = packet.getSourcePlayerUUID();
        BerserkerRageSoundPacket.Action action = packet.getAction();
        if (action == BerserkerRageSoundPacket.Action.START) {
            // Create and play a new music instance if not already active
            if (!activeMusic.containsKey(sourceUUID)) {
                // Configure fade duration as desired (e.g., 5 seconds fade-out when stopped)
                assert mc.level != null;
                Player source = mc.level.getPlayerByUUID(sourceUUID);
                if (source == null) return; // Do not play sound if source is not loaded

                if (mc.player != null && mc.player.distanceToSqr(source) > 256) return;

                FadingMusicInstance instance = new FadingMusicInstance(source, FalseionSounds.BERSERKER_RAGE.get(), ModConfig.VALUES.musicFadeOutLength);
                activeMusic.put(sourceUUID, instance);
                mc.getSoundManager().play(instance);
                assert mc.player != null;
                mc.player.displayClientMessage(
                        net.minecraft.network.chat.Component.translatable("component.music_playing.title")
                                     .append(Component.translatable("component.music.berserker_rage.name")), true);
            }
        } else if (action == BerserkerRageSoundPacket.Action.STOP) {
            // Trigger fade out if active
            FadingMusicInstance instance = activeMusic.get(sourceUUID);
            if (instance != null) {
                instance.triggerFadeOut();
                activeMusic.remove(sourceUUID);
            }
        }
    }
}
