package com.falseion_pantheon.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

public class ModConfig {

    public static final ForgeConfigSpec CONFIG;
    public static final ConfigValues VALUES;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        VALUES = new ConfigValues(builder);
        CONFIG = builder.build();
    }

    @SuppressWarnings("removal")
    public static void register() {
        ModLoadingContext.get().registerConfig(Type.COMMON, CONFIG);
    }

    public static class ConfigValues {
        public final ForgeConfigSpec.IntValue fratricideKillInterval; // in seconds
        public final ForgeConfigSpec.ConfigValue<String> fratricideDesignatedBearer; // new config value
        public final ForgeConfigSpec.ConfigValue<Boolean> fratricideRageImmortal;
        public final ForgeConfigSpec.ConfigValue<Boolean> fratricidePlayRageMusic;
        public final ForgeConfigSpec.ConfigValue<Boolean> banFratricideItemMovement;
        public final ForgeConfigSpec.DoubleValue musicFadeOutLength;

        public ConfigValues(ForgeConfigSpec.Builder builder) {
            builder.push("General Settings");
            fratricideKillInterval = builder
                    .comment("Interval (in seconds) to force a kill. Default is 300 seconds (5 minutes).")
                    .defineInRange("fratricideKillInterval", 300, 1, 3600);
            fratricideDesignatedBearer = builder
                    .comment("Designated bearer name for FRATRICIDE item. Leave empty to ignore condition.")
                    .define("fratricideDesignatedBearer", "");
            fratricideRageImmortal = builder
                    .comment("Designated bearer for FRATRICIDE item would be immortal under the Khorne's berserk rage:")
                    .define("fratricideRageImmortal", Boolean.TRUE);
            fratricidePlayRageMusic = builder
                    .comment("Play music when Khorne's berserk rage is ignited:")
                    .define("fratricidePlayRageMusic", Boolean.TRUE);
            banFratricideItemMovement = builder
                    .comment("If true, prevent moving or dropping FRATRICIDE outside the player's inventory")
                    .define("banFratricideItemMovement", Boolean.TRUE);
            musicFadeOutLength = builder
                    .comment("Fade out length for any music after using the gem's special ability.")
                    .defineInRange("musicFadeOutLength", 2.5f, 0f, 3600f);

            builder.pop();
        }
    }
}
