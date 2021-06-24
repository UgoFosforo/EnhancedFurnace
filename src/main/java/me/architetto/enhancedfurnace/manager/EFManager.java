package me.architetto.enhancedfurnace.manager;

import me.architetto.enhancedfurnace.config.ConfigManager;
import me.architetto.enhancedfurnace.obj.LightLocation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class EFManager {

    private static EFManager instance;

    private final HashMap<LightLocation, Long> enhancedFurnaceMap;

    private EFManager() {
        if(instance != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");

        enhancedFurnaceMap = new HashMap<>();
    }

    public static EFManager getInstance() {
        if(instance == null)
            instance = new EFManager();

        return instance;
    }

    public boolean addEF(Location location) {
        Block block = location.getBlock();

        if (!block.getType().equals(Material.FURNACE))
            return false;

        LightLocation ll = new LightLocation(location);

        if (enhancedFurnaceMap.containsKey(ll))
            return false;

        long time = System.currentTimeMillis();
        enhancedFurnaceMap.put(ll, time);
        ConfigManager.getInstance().addLocation(ConfigManager.getInstance().getConfig("EnFurnace.yml"), location, "" + time);
        return true;
    }

    public boolean removeEF(Location location) {
        LightLocation ll = new LightLocation(location);
        if (!enhancedFurnaceMap.containsKey(ll))
            return false;
        ConfigManager.getInstance().setData(ConfigManager.getInstance().getConfig("EnFurnace.yml"),enhancedFurnaceMap.get(ll).toString(), null);
        enhancedFurnaceMap.remove(ll);
        return true;
    }

    public boolean isEF(Location location) {
        return enhancedFurnaceMap.containsKey(new LightLocation(location));
    }

    public void loadEnF(boolean clearOld) {
        if (clearOld)
            enhancedFurnaceMap.clear();

        ConfigManager configManager = ConfigManager.getInstance();
        FileConfiguration fc = configManager.getConfig("EnFurnace.yml");

        fc.getKeys(false).forEach(s -> enhancedFurnaceMap
                .put(new LightLocation(configManager.getLocation(fc, s))
                        ,Long.parseLong(s)));


    }

}
