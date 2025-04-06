package com.falseion_pantheon.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FalseionSavedData extends SavedData {

    private static final String DATA_NAME = "fratricide_data";

    private final Map<UUID, Long> lastKillTimes = new HashMap<>();

    public static FalseionSavedData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                tag -> {
                    FalseionSavedData data = new FalseionSavedData();
                    data.load(tag);
                    return data;
                },
                FalseionSavedData::new,
                DATA_NAME
        );
    }

    private FalseionSavedData() {}

    public void setLastKill(UUID uuid, long time) {
        lastKillTimes.put(uuid, time);
        setDirty();
    }

    public long getLastKill(UUID uuid, long defaultTime) {
        return lastKillTimes.getOrDefault(uuid, defaultTime);
    }

    public void load(CompoundTag tag) {
        CompoundTag kills = tag.getCompound("lastKills");
        for (String key : kills.getAllKeys()) {
            lastKillTimes.put(UUID.fromString(key), kills.getLong(key));
        }
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        CompoundTag kills = new CompoundTag();
        for (Map.Entry<UUID, Long> entry : lastKillTimes.entrySet()) {
            kills.putLong(entry.getKey().toString(), entry.getValue());
        }
        tag.put("lastKills", kills);
        return tag;
    }
}
