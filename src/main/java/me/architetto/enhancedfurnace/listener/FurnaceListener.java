package me.architetto.enhancedfurnace.listener;

import me.architetto.enhancedfurnace.EnhancedFurnace;
import me.architetto.enhancedfurnace.config.SettingsHandler;
import me.architetto.enhancedfurnace.manager.EFManager;
import me.architetto.enhancedfurnace.message.Message;
import me.architetto.enhancedfurnace.obj.LightLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class FurnaceListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSmeltingEvent(FurnaceSmeltEvent event) {
        Location loc = event.getBlock().getLocation().toCenterLocation();
        if (EFManager.getInstance().isEF(loc)) {
            Material material = event.getResult().getType();
            event.setResult(new ItemStack(material,SettingsHandler.getInstance().getOutputMultiplier(material)));
            furnaceCookTimeUpdater(loc);
        }
    }

    @EventHandler
    public void onBurnEvent(FurnaceBurnEvent event) {
        if (EFManager.getInstance().isEF(event.getBlock().getLocation().toCenterLocation())) {
            event.setBurnTime((short) SettingsHandler.getInstance().getBurnTime(event.getFuel().getType()));
            Furnace furnace = (Furnace) event.getBlock().getState();
            ItemStack itemStack = furnace.getInventory().getSmelting();
            if (furnace.getCookTime() == 0 && Objects.nonNull(itemStack)) {
                furnaceCookTimeUpdater(event.getBlock().getLocation());
            }
        }
    }

    @EventHandler
    public void onFurnaceExtractEvent(FurnaceExtractEvent event) {
        if (EFManager.getInstance().isEF(event.getBlock().getLocation().toCenterLocation())) {
            event.setExpToDrop(event.getExpToDrop() * SettingsHandler.getInstance().getExpMultiplier());
        }
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void onEnFInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getClickedInventory().getLocation() != null) {
            if (EFManager.getInstance().isEF(event.getClickedInventory().getLocation().toCenterLocation())) {
                Furnace furnace = (Furnace) event.getClickedInventory().getLocation().getBlock().getState();
                if (furnace.getCookTime() != 0)
                    furnaceCookTimeUpdater(furnace.getLocation().toCenterLocation());
            }
        }
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if (EFManager.getInstance().removeEF(event.getBlock().getLocation().toCenterLocation())) {
            Bukkit.getConsoleSender()
                    .sendMessage(Message.ENF_BREAK.asString(new LightLocation(event.getBlock()
                            .getLocation().toCenterLocation()),
                            event.getPlayer().getName()));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST,ignoreCancelled = true)
    public void onBlockBreakByExplosion(EntityExplodeEvent event) {
        EFManager efManager = EFManager.getInstance();
        event.blockList()
                .stream()
                .map(Block::getLocation)
                .map(Location::toCenterLocation)
                .filter(efManager::removeEF)
                .forEach(loc -> Bukkit.getConsoleSender().sendMessage(Message.ENF_BREAK
                        .asString(new LightLocation(loc).toString(),
                                event.getEntityType())));
    }


    private void furnaceCookTimeUpdater(Location location) {
        //todo: re-implementare effetti particellari
        new BukkitRunnable() {
            @Override
            public void run() {
                Furnace furnace = (Furnace) location.getBlock().getState();
                ItemStack itemStack = furnace.getInventory().getSmelting();
                if (Objects.nonNull(itemStack)) {
                    furnace.setCookTimeTotal(SettingsHandler.getInstance().getCookSpeed(itemStack.getType()));
                    furnace.update();
                }
            }
        }.runTaskLater(EnhancedFurnace.plugin,1);

    }

}
