package com.falseion_pantheon;

import com.falseion_pantheon.config.ModConfig;
import com.falseion_pantheon.events.InventoryListener;
import com.falseion_pantheon.events.KillEventHandler;
import com.falseion_pantheon.registries.EffectsRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FalseionCredentials.Id)
public class Falseion {

    @SuppressWarnings("removal")
    public Falseion() {
        // Register the config
        ModConfig.register();

        FalseionItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FalseionSounds.SOUND_EVENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        EffectsRegistry.EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());

        // Register common setup and event handlers
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(new InventoryListener());
        MinecraftForge.EVENT_BUS.register(new KillEventHandler());
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Setup code if needed
    }
}
