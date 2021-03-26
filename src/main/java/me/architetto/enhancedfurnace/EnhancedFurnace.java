package me.architetto.enhancedfurnace;

import me.architetto.enhancedfurnace.command.CommandManager;
import me.architetto.enhancedfurnace.config.ConfigManager;
import me.architetto.enhancedfurnace.config.SettingsHandler;
import me.architetto.enhancedfurnace.listener.FurnaceListener;
import me.architetto.enhancedfurnace.message.LocalizationManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class EnhancedFurnace extends JavaPlugin {

    public static Plugin plugin;


    @Override
    public void onEnable() {

        plugin = this;
        ConfigManager.getInstance().setPlugin(this);

        Bukkit.getConsoleSender().sendMessage("===================[  " + ChatColor.RED + "Enhanced Furnace" + ChatColor.RESET + "  ]====================");

        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[]" + ChatColor.RESET + " Loading messages...");
        loadLocalization();

        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[]" + ChatColor.RESET + " Loading settings...");
        loadSettings();

        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[]" + ChatColor.RESET + " Loading commands...");
        loadCommands();

        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[]" + ChatColor.RESET + " Loading listeners...");
        loadListeners();

        Bukkit.getConsoleSender().sendMessage("=============================================================");




    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void loadCommands() {
        Objects.requireNonNull(getCommand("enhancedfurnace")).setExecutor(new CommandManager());
    }

    public void loadListeners() {
        getServer().getPluginManager().registerEvents(new FurnaceListener(),this);
    }

    public void loadLocalization() {
        LocalizationManager.getInstance().loadLanguageFile();
    }

    public void loadSettings() {
        SettingsHandler.getInstance().load();
    }
}
