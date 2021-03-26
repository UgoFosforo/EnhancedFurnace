package me.architetto.enhancedfurnace.message;

import me.architetto.enhancedfurnace.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class LocalizationManager {

    private static LocalizationManager localizationManager;

    private Map<String, String> strings;

    private LocalizationManager() {
        if (localizationManager != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        this.strings = new HashMap<>();

    }

    public static LocalizationManager getInstance() {
        if(localizationManager == null) {
            localizationManager = new LocalizationManager();
        }
        return localizationManager;
    }

    public void loadLanguageFile() {

        FileConfiguration messages = ConfigManager.getInstance().getConfig("Settings.yml");

        for (String key : messages.getConfigurationSection("strings").getKeys(false)) {
            this.strings.put(key, messages.getString("strings." + key));
        }
    }

    public void reload() {
        this.strings.clear();
        loadLanguageFile();
    }

    public String localize(String key) {
        return this.strings.containsKey(key) ? this.strings.get(key) : ChatColor.RED + "No translation present for " + key;
    }

}
