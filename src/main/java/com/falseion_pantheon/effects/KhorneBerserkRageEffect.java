package com.falseion_pantheon.effects;

import com.falseion_pantheon.network.NetworkHandler;
import com.falseion_pantheon.network.sounds.StopMusicPacket;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class KhorneBerserkRageEffect extends MobEffect {

    public KhorneBerserkRageEffect() {
        // Classify as a harmful effect if desired, or beneficial based on your design.
        // Here, we choose BENEFICIAL because of the immortality and speed bonus.
        // However, the maximum blindness makes it a curse.
        super(MobEffectCategory.BENEFICIAL, 0xFF0000); // Color set to bloody red.
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // Every tick, "refresh" the vanilla effects on the player.
        // Apply Blindness with a short duration; amplifier doesn't matter for blindness.
        if (!entity.hasEffect(MobEffects.DARKNESS) ||
                Objects.requireNonNull(entity.getEffect(MobEffects.DARKNESS)).getDuration() < 30) {
            // effect, duration (ticks), amplifier, ambient, show
            entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 60, 255, false, false));
        }

        if (!entity.hasEffect(MobEffects.BLINDNESS) ||
                Objects.requireNonNull(entity.getEffect(MobEffects.BLINDNESS)).getDuration() < 30) {
            // effect, duration (ticks), amplifier, ambient, show
            entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 255, false, false));
        }

        if (!entity.hasEffect(MobEffects.MOVEMENT_SPEED) ||
                Objects.requireNonNull(entity.getEffect(MobEffects.MOVEMENT_SPEED)).getDuration() < 30) {
            // effect, duration (ticks), amplifier, ambient, show
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 1, false, false));
        }
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity entity, @NotNull AttributeMap attributes, int amplifier) {
        super.removeAttributeModifiers(entity, attributes, amplifier);
//        if (!entity.level.isClientSide && entity instanceof Player) {
//            NetworkHandler.sendToTrackingPlayers(
//                    new StopMusicPacket(entity.getId()),
//                    (Player)entity
//            );
//        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // Ensure this tick function is called every tick.
        return true;
    }
}
