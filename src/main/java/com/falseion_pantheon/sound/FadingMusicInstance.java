package com.falseion_pantheon.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.UUID;

public class FadingMusicInstance extends AbstractTickableSoundInstance {
    private final Player sourcePlayer;
    private final float fadeDurationSeconds; // total fade duration in seconds
    private float fadeTime = 0f;
    private boolean fading = false;

    public FadingMusicInstance(Player player, SoundEvent sound, float fadeDurationSeconds) {
        super(sound, SoundSource.MUSIC, RandomSource.create());
        this.sourcePlayer = player;
        this.fadeDurationSeconds = fadeDurationSeconds;
        this.looping = false;  // we won't loop the music until fade-out is triggered
        this.volume = 1.0f;
        this.pitch = 1.0f;
        this.delay = 0;
        updatePosition();
    }

    public FadingMusicInstance(Player player, SoundEvent sound, ForgeConfigSpec.DoubleValue fadeDurationSeconds) {
        super(sound, SoundSource.MUSIC, RandomSource.create());
        this.sourcePlayer = player;
        this.fadeDurationSeconds = fadeDurationSeconds.get().floatValue();
        this.looping = false;  // we won't loop the music until fade-out is triggered
        this.volume = 1.0f;
        this.pitch = 1.0f;
        this.delay = 0;
        updatePosition();
    }

    @Override
    public void tick() {
        updatePosition();
        if (fading) {
            fadeTime += 1.0f;
            float totalTicks = fadeDurationSeconds * 20f;
            this.volume = Math.max(0f, 1.0f - (fadeTime / totalTicks));
            if (fadeTime >= totalTicks) {
                Minecraft.getInstance().getSoundManager().stop(this);
            }
        }
    }

    private void updatePosition() {
        if (sourcePlayer != null) {
            this.x = sourcePlayer.getX();
            this.y = sourcePlayer.getY();
            this.z = sourcePlayer.getZ();
        }
    }

    public void triggerFadeOut() {
        fading = true;
        fadeTime = 0f;
    }
}
