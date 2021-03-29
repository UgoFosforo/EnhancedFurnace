package me.architetto.enhancedfurnace.command.cmd;

import me.architetto.enhancedfurnace.command.CommandName;
import me.architetto.enhancedfurnace.command.SubCommand;
import me.architetto.enhancedfurnace.config.SettingsHandler;
import me.architetto.enhancedfurnace.message.Message;
import org.bukkit.entity.Player;

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

    @Override
    public void perform(Player sender, String[] args) {

        SettingsHandler.getInstance().reload();
        Message.SUCCESS_RELOAD.send(sender);

    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
