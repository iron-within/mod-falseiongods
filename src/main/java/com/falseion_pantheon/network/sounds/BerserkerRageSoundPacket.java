package com.falseion_pantheon.network.sounds;

import com.falseion_pantheon.events.client.ClientSoundManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.UUID;
import java.util.function.Supplier;

public class BerserkerRageSoundPacket {
    public enum Action { START, STOP }

    private final UUID sourcePlayerUUID;
    private final Action action;

    public BerserkerRageSoundPacket(UUID sourcePlayerUUID, Action action) {
        this.sourcePlayerUUID = sourcePlayerUUID;
        this.action = action;
    }

    public static BerserkerRageSoundPacket decode(FriendlyByteBuf buf) {
        UUID uuid = buf.readUUID();
        Action action = buf.readEnum(BerserkerRageSoundPacket.Action.class);
        return new BerserkerRageSoundPacket(uuid, action);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(sourcePlayerUUID);
        buf.writeEnum(action);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Handle packet on client side
            ClientSoundManager.handleBerserkerRageSoundPacket(this);
        });
        ctx.get().setPacketHandled(true);
    }

    public UUID getSourcePlayerUUID() {
        return sourcePlayerUUID;
    }

    public Action getAction() {
        return action;
    }
}
