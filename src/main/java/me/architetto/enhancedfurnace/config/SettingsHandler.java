package me.architetto.enhancedfurnace.config;

import me.architetto.enhancedfurnace.message.LocalizationManager;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;

public class SettingsHandler {

    private static SettingsHandler instance;

    private final HashMap<Material,Integer> outputMultiplier;
    private final HashMap<Material, Integer> customCookSpeed;

    private int defaultCookSpeed;
    private int expMultiplier;

    private double setFireonBurnProbability;

    private SettingsHandler() {
        if(instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }

        outputMultiplier = new HashMap<>();
        customCookSpeed = new HashMap<>();

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
        loadCustomCookSpeed(fc);

        defaultCookSpeed = fc.getInt("anhanced_furnace.default_cook_speed", 200);
        expMultiplier = fc.getInt("anhanced_furnace.exp_multiplier", 1);
        setFireonBurnProbability = fc.getDouble("set_fire_on_burn_probability",-1);

    }

    public void reload() {
        outputMultiplier.clear();
        customCookSpeed.clear();

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

    private void loadCustomCookSpeed(FileConfiguration fc) {
        List<String> outputBoostList = fc.getStringList("anhanced_furnace.custom_cook_speed");

        if (!outputBoostList.isEmpty()) {
            outputBoostList.forEach(s -> {
                String[] value = s.split(",");
                customCookSpeed.put(Material.getMaterial(value[0]),Integer.parseInt(value[1]));
            });
        }
    }

    public int getExpMultiplier() {
        return expMultiplier;
    }

    public int getOutputMultiplier(Material m) {
        return outputMultiplier.getOrDefault(m, 1);
    }

    public int getCookSpeed(Material m) {
        return customCookSpeed.getOrDefault(m, defaultCookSpeed);
    }

    public double getSetFireonBurnProbability() {
        return setFireonBurnProbability;
    }
}
