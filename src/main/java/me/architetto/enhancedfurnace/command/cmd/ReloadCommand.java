package me.architetto.enhancedfurnace.command.cmd;

import me.architetto.enhancedfurnace.command.CommandName;
import me.architetto.enhancedfurnace.command.SubCommand;
import me.architetto.enhancedfurnace.config.SettingsHandler;
import me.architetto.enhancedfurnace.manager.EFManager;
import me.architetto.enhancedfurnace.message.Message;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;

import java.util.Collections;
import java.util.List;

public class ReloadCommand extends SubCommand {
    @Override
    public String getName() {
        return CommandName.RELOAD_CMD;
    }

    @Override
    public String getDescription() {
        return Message.RELOAD_COMMAND.asString();
    }

    @Override
    public String getSyntax() {
        return "/enf " + CommandName.RELOAD_CMD;
    }

    @Override
    public String getPermission() {
        return "enhancedfurnace.reload";
    }

    @Override
    public int getArgsRequired() {
        return 0;
    }

    private static final List<String> param = Collections.singletonList(
            "-enf"
    );

    @Override
    public void perform(Player sender, String[] args) {

        SettingsHandler.getInstance().reload();

        if (args.length >= 2 && args[1].equalsIgnoreCase("-enf"))
            EFManager.getInstance().loadEnF(true);

        Message.SUCCESS_RELOAD.send(sender);
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {

        if (args.length == 2)
            return param;

        return null;
    }
}
