package me.architetto.enhancedfurnace.util;

import org.bukkit.ChatColor;

import java.util.Collections;

public class MessageUtil {

    public static String commandsInfo() {
        return  ChatColor.YELLOW + "[*]----------------[ " +
                ChatColor.DARK_AQUA + ChatColor.BOLD + "COMMANDS INFO" +
                ChatColor.YELLOW + " ]----------------[*]";
    }

    public static String chatFooter() {
        return  ChatColor.YELLOW + String.join("", Collections.nCopies(53, "-"));
    }

    public static String formatListMessage(String message) {
        message = ChatColor.GRAY + "[] " + ChatColor.RESET + message;
        return message;
    }

    public static String rewritePlaceholders(String input) {
        int i = 0;
        while (input.contains("{}")) {
            input = input.replaceFirst("\\{\\}", "{" + i++ + "}");
        }
        return input;
    }

    public static String format(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
