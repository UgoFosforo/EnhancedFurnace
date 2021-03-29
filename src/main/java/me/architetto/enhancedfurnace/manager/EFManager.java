package me.architetto.enhancedfurnace.manager;

import me.architetto.enhancedfurnace.obj.LightLocation;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Set;

public class EFManager {

    private static EFManager instance;

    private final Set<LightLocation> enhancedFurnace;

    private EFManager() {
        if(instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }

        enhancedFurnace = new HashSet<>();

    }

    public static EFManager getInstance() {
        if(instance == null) {
            instance = new EFManager();
        }
        return instance;
    }

    public boolean addEF(Location location) {
       return enhancedFurnace.add(new LightLocation(location));
    }

    public boolean removeEF(Location location) {
        return enhancedFurnace.remove(new LightLocation(location));
    }

    public boolean isEF(Location location) {
        return enhancedFurnace.contains(new LightLocation(location));
    }

    public boolean isEF(Block block) {
        return enhancedFurnace.contains(new LightLocation(block.getLocation().toCenterLocation()));
    }



}
