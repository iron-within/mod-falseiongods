package com.falseion_pantheon.registries;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.falseion_pantheon.effects.KhorneBerserkRageEffect;

public class EffectsRegistry {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "falseion_pantheon");

    public static final RegistryObject<MobEffect> KHORNE_BERSERK_RAGE =
            EFFECTS.register("khorne_berserk_rage", KhorneBerserkRageEffect::new);
}
