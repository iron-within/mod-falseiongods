package com.falseion_pantheon.network.sounds;

import com.falseion_pantheon.network.handler.ClientPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StopMusicPacket {
    private final int entityId;

    public StopMusicPacket(int entityId) {
        this.entityId = entityId;
    }

    public static void encode(StopMusicPacket msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.entityId);
    }

    public static StopMusicPacket decode(FriendlyByteBuf buffer) {
        return new StopMusicPacket(buffer.readInt());
    }

    public static void handle(StopMusicPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Client-side handling
            if (ctx.get().getDirection().getReceptionSide().isClient()) {
//                ClientPacketHandler.handleStopMusic(msg);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public int getEntityId() {
        return entityId;
    }
}