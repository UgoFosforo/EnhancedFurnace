package me.architetto.enhancedfurnace.config;

import me.architetto.enhancedfurnace.message.LocalizationManager;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;

public class SettingsHandler {

    private static SettingsHandler instance;

    private final HashMap<Material,Integer> outputMultiplier;

    private double fuelSpeedMultiplier;
    private double expMultiplier;

    private SettingsHandler() {
        if(instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }

        outputMultiplier = new HashMap<>();

    }

    public static SettingsHandler getInstance() {
        if(instance == null) {
            instance = new SettingsHandler();
        }
        return instance;
    }

    public void load() {
        FileConfiguration fc = ConfigManager.getInstance().getConfig("Settings.yml");

        loadOutputBoost(fc);
        fuelSpeedMultiplier = fc.getDouble("anhanced_furnace.speed_multiplier");
        expMultiplier = fc.getDouble("anhanced_furnace.exp_multiplier");

    }

    public void reload() {
        outputMultiplier.clear();

        ConfigManager.getInstance().reloadConfigs();
        LocalizationManager.getInstance().reload();

        load();
    }

    private void loadOutputBoost(FileConfiguration fc) {
        List<String> outputBoostList = fc.getStringList("anhanced_furnace.result_multiplier");

        if (!outputBoostList.isEmpty()) {
            outputBoostList.forEach(s -> {
                String[] value = s.split(",");
                outputMultiplier.put(Material.getMaterial(value[0]),Integer.parseInt(value[1]));
            });
        }
    }

    public double getExpMultiplier() {
        return expMultiplier;
    }

    public double getFuelSpeedMultiplier() {
        return fuelSpeedMultiplier;
    }

    public int getOutputMultiplier(Material m) {
        return outputMultiplier.getOrDefault(m, 1);
    }
}
