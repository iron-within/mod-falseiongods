package com.falseion_pantheon.network.handler;

import com.falseion_pantheon.Falseion;
import com.falseion_pantheon.FalseionSounds;
import com.falseion_pantheon.network.sounds.StartMusicPacket;
import com.falseion_pantheon.network.sounds.StopMusicPacket;
import com.falseion_pantheon.sound.FadingMusicInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import java.util.HashMap;
import java.util.Map;

public class ClientPacketHandler {
    public static final Map<Integer, FadingMusicInstance> activeSounds = new HashMap<>();

//    public static void handleStartMusic(StartMusicPacket packet) {
//        Falseion.LOGGER.info(String.format("Received start music packet for entity:" + packet.getEntityId()));
//        assert Minecraft.getInstance().level != null;
//        Entity entity = Minecraft.getInstance().level.getEntity(packet.getEntityId());
//        if (entity instanceof Player && !activeSounds.containsKey(packet.getEntityId())) {
//            FadingMusicInstance sound = new FadingMusicInstance(
//                    FalseionSounds.BERSERKER_RAGE.get(),
//                    entity,
//                    1.0F,
//                    1.0F
//            );
//            Minecraft.getInstance().getSoundManager().play(sound);
//            activeSounds.put(packet.getEntityId(), sound);
//        }
//    }
//
//    public static void handleStopMusic(StopMusicPacket packet) {
//        FadingMusicInstance sound = activeSounds.get(packet.getEntityId());
//        if (sound != null) {
//            sound.fadeOut();
//            activeSounds.remove(packet.getEntityId());
//        }
//    }
}