package com.falseion_pantheon.events.client;

import com.falseion_pantheon.FalseionSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = "falseion_pantheon", value = Dist.CLIENT)
public class HeartbeatSoundHandler {

    // Delay between heartbeats in ticks (20 ticks = 1 second).
    private static final int HEARTBEAT_INTERVAL = 40; // Adjust to taste.
    private static int tickCounter = 0;
    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }
        LocalPlayer player = mc.player;

        // Check if the player has Khorne's Berserk Rage effect.
        if (player.hasEffect(com.falseion_pantheon.registries.EffectsRegistry.KHORNE_BERSERK_RAGE.get())) {
            tickCounter++;
            if (tickCounter >= HEARTBEAT_INTERVAL) {
                tickCounter = 0;
                // Randomly select one of the heartbeat sounds.
                int choice = RANDOM.nextInt(4);
                switch (choice) {
                    case 0:
                        player.level.playLocalSound(player.getX(), player.getY(), player.getZ(),
                                FalseionSounds.HEARTBEAT1.get(), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                        break;
                    case 1:
                        player.level.playLocalSound(player.getX(), player.getY(), player.getZ(),
                                FalseionSounds.HEARTBEAT2.get(), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                        break;
                    case 2:
                        player.level.playLocalSound(player.getX(), player.getY(), player.getZ(),
                                FalseionSounds.HEARTBEAT3.get(), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                        break;
                    case 3:
                        player.level.playLocalSound(player.getX(), player.getY(), player.getZ(),
                                FalseionSounds.HEARTBEAT4.get(), SoundSource.AMBIENT, 1.0F, 1.0F, false);
                        break;
                }
            }
        } else {
            tickCounter = 0;
        }
    }
}
