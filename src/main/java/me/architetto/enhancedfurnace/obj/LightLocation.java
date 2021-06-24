package me.architetto.enhancedfurnace.obj;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Objects;
import java.util.UUID;

public class LightLocation {

    private final Vector vector;
    private final String worldName;
    private final UUID worldUUID;

    public LightLocation(Location location) {
        this.vector = location.toVector();
        this.worldName = location.getWorld().getName();
        this.worldUUID = location.getWorld().getUID();
    }

    public Location loc() {
        return new Location(Bukkit.getWorld(worldName), vector.getX(), vector.getY(), vector.getZ());
    }

    public Vector getVector() {
        return vector;
    }

    public String getWorldName() {
        return worldName;
    }

    public UUID getWorldUUID() {
        return worldUUID;
    }

    @Override
    public String toString() {
        return worldName + "," + vector.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LightLocation ll = (LightLocation) o;
        return Objects.equals(vector, ll.vector) &&
                Objects.equals(worldUUID, ll.worldUUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vector, worldUUID);
    }

}
