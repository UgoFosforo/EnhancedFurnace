package me.architetto.enhancedfurnace.listener;

import me.architetto.enhancedfurnace.EnhancedFurnace;
import me.architetto.enhancedfurnace.config.SettingsHandler;
import me.architetto.enhancedfurnace.effect.ParticleEffectsManager;
import me.architetto.enhancedfurnace.manager.EFManager;
import me.architetto.enhancedfurnace.message.Message;
import me.architetto.enhancedfurnace.obj.LightLocation;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class FurnaceListener implements Listener {

    @EventHandler
    public void onFurnaceSmelt(FurnaceSmeltEvent e) {
        Furnace f = (Furnace) e.getBlock().getState();

        if (EFManager.getInstance().isEF(f.getLocation().toCenterLocation())) {
            Material material = e.getResult().getType();
            e.setResult(new ItemStack(material,SettingsHandler.getInstance().getOutputMultiplier(material)));
        }

        if (f.getCookTime() == 0 && f.getCookTimeTotal() != 0) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(EnhancedFurnace.getPlugin(EnhancedFurnace.class),
                    () -> {
                        Furnace furnace = (Furnace) e.getBlock().getState();
                        FurnaceStartSmeltEvent event = new FurnaceStartSmeltEvent(furnace);
                        Bukkit.getServer().getPluginManager().callEvent(event);
                },3L);
        }

    }

    @EventHandler
    public void onFurnaceBurnItem(FurnaceBurnEvent e) {
        Furnace f = (Furnace) e.getBlock().getState();
        if (f.getCookTime() == 0) {
            //Just started to cook an item, since this tells how long it has been cooking.
            FurnaceStartSmeltEvent event = new FurnaceStartSmeltEvent(f);
            Bukkit.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onFurnaceStartSmelt(FurnaceStartSmeltEvent e) {
        Bukkit.getConsoleSender().sendMessage("Chiamato evento FurnaceStartSmeltEvent");

        Furnace f = e.getFurnace();
        ItemStack itemStack = f.getInventory().getSmelting();
        if (itemStack == null) return;
        f.setCookTimeTotal(SettingsHandler.getInstance().getCookSpeed(itemStack.getType()));
        f.update();

        if (EFManager.getInstance().isEF(f.getLocation().toCenterLocation())
                && new Random().nextDouble() < SettingsHandler.getInstance().getSetFireonBurnProbability()) {
            f.getLocation().getNearbyPlayers(5).forEach(player -> player.setFireTicks(100));
            ParticleEffectsManager.getInstance().enfHotBurnExplosion(f.getBlock());
        }

    }

    // Quando la fornace è in funzione non puoi aggiungere e/o rimuovere items
    @EventHandler
    public void onFurnaceInventoryClickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getClickedInventory().getLocation() != null) {
            if (EFManager.getInstance().isEF(event.getClickedInventory().getLocation().toCenterLocation())) {
                Furnace furnace = (Furnace) event.getClickedInventory().getLocation().getBlock().getState();
                if (furnace.getCookTime() != 0)
                    event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFurnaceExtractEvent(FurnaceExtractEvent event) {
        if (EFManager.getInstance().isEF(event.getBlock().getLocation().toCenterLocation())) {
            event.setExpToDrop(event.getExpToDrop() * SettingsHandler.getInstance().getExpMultiplier());
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

}
