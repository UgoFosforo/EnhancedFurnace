package me.architetto.enhancedfurnace.message;

import me.architetto.enhancedfurnace.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public enum Message {

    PREFIX("prefix", false),

    FURNACE_ADDED("furnace_added",true),
    FURNACE_REMOVED("furnace_removed",true),

    SUCCESS_RELOAD("success_reload", true),

    ERR_BLOCK_NOT_FOUND("err_block_not_found",true),
    ERR_IS_ENF("err_is_enf",true),
    ERR_NOT_ENF("err_not_enf",true),

    //COMMAND DESCRIPTION
    ADD_COMMAND("add_command",false),
    REMOVE_COMMAND("remove_command",false),
    RELOAD_COMMAND("reload_command",false),

    //LOG
    ENF_BREAK("enf_break",true);

    private final String message;
    private final boolean showPrefix;
    private final LocalizationManager localizationManager;

    Message(String message, boolean showPrefix) {
        this.message = MessageUtil.rewritePlaceholders(message);
        this.showPrefix = showPrefix;
        this.localizationManager = LocalizationManager.getInstance();
    }

    public void send(CommandSender sender, Object... objects) {
        sender.sendMessage(asString(objects));
    }

    public void broadcast(Object... objects) {
        Bukkit.getServer().broadcastMessage(asString(objects));
    }

    public String asString(Object... objects) {
        return format(objects);
    }

    private String format(Object... objects) {
        String string = localizationManager.localize(this.message);
        if(this.showPrefix) {
            string = localizationManager.localize(PREFIX.message) + " " + string;
        }
        for (int i = 0; i < objects.length; i++) {
            Object o = objects[i];
            string = string.replace("{" + i + "}", String.valueOf(o));
        }
        return ChatColor.translateAlternateColorCodes('&', string);
    }


}
