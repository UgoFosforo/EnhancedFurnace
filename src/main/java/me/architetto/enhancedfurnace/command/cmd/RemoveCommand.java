package me.architetto.enhancedfurnace.command.cmd;

import com.destroystokyo.paper.block.TargetBlockInfo;
import me.architetto.enhancedfurnace.command.CommandName;
import me.architetto.enhancedfurnace.command.SubCommand;
import me.architetto.enhancedfurnace.manager.EFManager;
import me.architetto.enhancedfurnace.message.Message;
import me.architetto.enhancedfurnace.obj.LightLocation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class RemoveCommand extends SubCommand {
    @Override
    public String getName() {
        return CommandName.REMOVE_CMD;
    }

    @Override
    public String getDescription() {
        return Message.REMOVE_COMMAND.asString();
    }

    @Override
    public String getSyntax() {
        return "/enf " + CommandName.REMOVE_CMD;
    }

    @Override
    public String getPermission() {
        return "enhancedfurnace.remove";
    }

    @Override
    public int getArgsRequired() {
        return 0;
    }

    @Override
    public void perform(Player sender, String[] args) {

        //todo: -all per rimuovere tutte le enhanced furnace

        Block block = sender.getTargetBlock(5, TargetBlockInfo.FluidMode.NEVER);

        if (Objects.isNull(block) || !block.getType().equals(Material.FURNACE)) {
            Message.ERR_BLOCK_NOT_FOUND.send(sender);
            return;
        }

        Location location = block.getLocation().toCenterLocation();

        if (EFManager.getInstance().removeEF(location))
            Message.FURNACE_REMOVED.send(sender,new LightLocation(location).toString());
        else
            Message.ERR_NOT_ENF.send(sender);

    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
