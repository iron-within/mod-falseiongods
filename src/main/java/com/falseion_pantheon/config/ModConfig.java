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

            builder.pop();
        }
    }
}
