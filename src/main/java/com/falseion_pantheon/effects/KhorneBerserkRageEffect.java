package com.falseion_pantheon.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

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
            entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 60, 0, false, false));
        }

        if (!entity.hasEffect(MobEffects.MOVEMENT_SPEED) ||
                Objects.requireNonNull(entity.getEffect(MobEffects.MOVEMENT_SPEED)).getDuration() < 30) {
            // effect, duration (ticks), amplifier, ambient, show
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 1, false, false));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // Ensure this tick function is called every tick.
        return true;
    }
}
