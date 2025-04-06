package com.falseion_pantheon;

import net.minecraft.world.damagesource.DamageSource;

public class FalseionDamageTypes {
    public static final DamageSource FRATRICIDE_DAMAGE = new DamageSource("fratricide") {
        @Override
        public boolean scalesWithDifficulty() {
            return false;
        }

        @Override
        public boolean isBypassArmor() {
            return true;
        }

        @Override
        public boolean isCreativePlayer() {
            return false; // Still allow killing creative
        }
    }.bypassInvul().bypassArmor();
}

