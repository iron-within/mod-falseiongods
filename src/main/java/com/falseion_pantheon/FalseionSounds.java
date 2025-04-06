package com.falseion_pantheon;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FalseionSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FalseionCredentials.Id);

    public static final RegistryObject<SoundEvent> HEARTBEAT1 = SOUND_EVENTS.register("heartbeat1",
            () -> new SoundEvent(new ResourceLocation(FalseionCredentials.Id, "heartbeat1")));
    public static final RegistryObject<SoundEvent> HEARTBEAT2 = SOUND_EVENTS.register("heartbeat2",
            () -> new SoundEvent(new ResourceLocation(FalseionCredentials.Id, "heartbeat2")));
    public static final RegistryObject<SoundEvent> HEARTBEAT3 = SOUND_EVENTS.register("heartbeat3",
            () -> new SoundEvent(new ResourceLocation(FalseionCredentials.Id, "heartbeat3")));
    public static final RegistryObject<SoundEvent> HEARTBEAT4 = SOUND_EVENTS.register("heartbeat4",
            () -> new SoundEvent(new ResourceLocation(FalseionCredentials.Id, "heartbeat4")));

    public static final RegistryObject<SoundEvent> BERSERKER_RAGE = SOUND_EVENTS.register("berserker_rage",
            () -> new SoundEvent(new ResourceLocation(FalseionCredentials.Id, "berserker_rage")));
}
