package com.falseion_pantheon.network.sounds;

import com.falseion_pantheon.Falseion;
import com.falseion_pantheon.network.handler.ClientPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StartMusicPacket {
    private final int entityId;

    public StartMusicPacket(int entityId) {
        this.entityId = entityId;
    }

    public static void encode(StartMusicPacket msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.entityId);
    }

    public static StartMusicPacket decode(FriendlyByteBuf buffer) {
        return new StartMusicPacket(buffer.readInt());
    }

    public static void handle(StartMusicPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            try {
//                ClientPacketHandler.handleStartMusic(msg);
            } catch (Exception e) {
                Falseion.LOGGER.warning("Failed to handle StartMusicPacket:" + e.getMessage());
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public int getEntityId() {
        return entityId;
    }
}