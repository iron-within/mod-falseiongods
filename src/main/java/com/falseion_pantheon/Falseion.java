package com.falseion_pantheon;

import com.falseion_pantheon.config.ModConfig;
import com.falseion_pantheon.events.InventoryListener;
import com.falseion_pantheon.events.KillEventHandler;
import com.falseion_pantheon.network.NetworkHandler;
import com.falseion_pantheon.registries.EffectsRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.logging.Logger;

@Mod(FalseionCredentials.Id)
public class Falseion {
    public static Logger LOGGER = Logger.getLogger(FalseionCredentials.Id);

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

        // Register the network messages
        NetworkHandler.registerMessages();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Setup code if needed
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        Falseion.LOGGER.info("Network channel registered with ID:" + NetworkHandler.CHANNEL.toString());
    }
}
