package de.fnafhc.apertureproject.utils;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;

import java.util.Map;
import java.util.UUID;

import static de.fnafhc.apertureproject.Apertureproject.*;

public class WorldSave extends PersistentState {

    private RegistryKey<World> worldKey;

    // Speichern der HashMaps und des RegistryKey in NBT
    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        // Speichern des RegistryKey der Welt
        if (worldKey != null) {
            nbt.putString("WorldKey", worldKey.getValue().toString());
        }

        // Speichern von portalLoc1
        NbtCompound portalLoc1Nbt = new NbtCompound();
        for (Map.Entry<ServerPlayerEntity, Location> entry : portalLoc1.entrySet()) {
            portalLoc1Nbt.put(entry.getKey().getUuidAsString(), entry.getValue().toNbt());
        }
        nbt.put("PortalLoc1", portalLoc1Nbt);

        // Speichern von portalLoc2
        NbtCompound portalLoc2Nbt = new NbtCompound();
        for (Map.Entry<ServerPlayerEntity, Location> entry : portalLoc2.entrySet()) {
            portalLoc2Nbt.put(entry.getKey().getUuidAsString(), entry.getValue().toNbt());
        }
        nbt.put("PortalLoc2", portalLoc2Nbt);

        return nbt;
    }

    // Laden der HashMaps und des RegistryKey aus NBT
    public static WorldSave fromNbt(NbtCompound nbt) {
        WorldSave saveData = new WorldSave();

        // Laden des RegistryKey der Welt
        if (nbt.contains("WorldKey")) {
        }

        // Laden von portalLoc1
        NbtCompound portalLoc1Nbt = nbt.getCompound("PortalLoc1");
        for (String uuid : portalLoc1Nbt.getKeys()) {
            UUID playerUuid = UUID.fromString(uuid);
            ServerPlayerEntity player = server.getPlayerManager().getPlayer(uuid);
            if (player != null) {
                Location location = Location.fromNbt(portalLoc1Nbt.getCompound(uuid));
                portalLoc1.put(player, location);
            }
        }

        // Laden von portalLoc2
        NbtCompound portalLoc2Nbt = nbt.getCompound("PortalLoc2");
        for (String uuid : portalLoc2Nbt.getKeys()) {
            UUID playerUuid = UUID.fromString(uuid);
            ServerPlayerEntity player = server.getPlayerManager().getPlayer(uuid);
            if (player != null) {
                Location location = Location.fromNbt(portalLoc2Nbt.getCompound(uuid));
                portalLoc2.put(player, location);
            }
        }

        return saveData;
    }

    // Setze den RegistryKey der Welt
    public void setWorld(ServerWorld world) {
        this.worldKey = world.getRegistryKey(); // Setze den RegistryKey der Welt
        this.markDirty(); // Markiert die Daten als geändert
    }

    // Methode um die aktuelle Welt anhand des RegistryKeys zu erhalten
    public ServerWorld getWorld(MinecraftServer server) {
        return server.getWorld(worldKey); // Gibt die Welt zurück
    }
}
