package com.falseion_pantheon.network;

import com.falseion_pantheon.network.sounds.BerserkerRageSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("falseion_pantheon", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void registerMessages() {
        // Register BerserkerRageSoundPacket with a unique packet id.
        CHANNEL.registerMessage(packetId++,
                BerserkerRageSoundPacket.class,
                BerserkerRageSoundPacket::encode,
                BerserkerRageSoundPacket::decode,
                BerserkerRageSoundPacket::handle);
    }

    /**
     * Returns a PacketDistributor.TargetPoint for sending packets to players near the given ServerPlayer.
     * @param player the source ServerPlayer
     * @return a target point with a 32-block radius in the same dimension.
     */
    public static PacketDistributor.TargetPoint targetPoint(ServerPlayer player) {
        Level level = player.getLevel();
        return new PacketDistributor.TargetPoint(
                player.getX(),
                player.getY(),
                player.getZ(),
                32, // Radius in blocks
                level.dimension()
        );
    }

    /**
     * Send a message to all players near the given source player.
     * @param player The source player whose position is used for targeting.
     * @param message The message to send.
     * @param <MSG> The type of the message.
     */
    public static <MSG> void sendToAllAround(ServerPlayer player, MSG message) {
        CHANNEL.send(PacketDistributor.NEAR.with(() -> targetPoint(player)), message);
    }

    public static void sendToTrackingPlayers(Object packet, LivingEntity entity) {
        CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), packet);
    }
}
