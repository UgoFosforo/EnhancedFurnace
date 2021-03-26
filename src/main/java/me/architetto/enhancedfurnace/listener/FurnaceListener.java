package me.architetto.enhancedfurnace.listener;

import me.architetto.enhancedfurnace.effect.ParticleEffectsManager;
import me.architetto.enhancedfurnace.manager.EFManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;

public class FurnaceListener implements Listener {

    private final int resultBoost = 5;

    @EventHandler
    public void onSmeltingEvent(FurnaceSmeltEvent event) {
        if (EFManager.getInstance().isEF(event.getBlock().getLocation().toCenterLocation())) {
            Furnace furnace = (Furnace) event.getBlock().getState();
            ItemStack itemStack = furnace.getInventory().getResult();

            if (itemStack == null || itemStack.getAmount() < 64 - resultBoost)
                event.setResult(new ItemStack(event.getResult().getType(),resultBoost));
            else
                event.setResult(new ItemStack(event.getResult().getType(),64 - itemStack.getAmount()));

            ParticleEffectsManager.getInstance().smeltDone(event.getBlock());

        }
    }

    @EventHandler
    public void onBurnEvent(FurnaceBurnEvent event) {
        if (EFManager.getInstance().isEF(event.getBlock().getLocation().toCenterLocation())) {
            Material material = event.getFuel().getType();
            Furnace furnace = (Furnace) event.getBlock().getState();
            switch (material) {
                case COAL:
                    break;
                case CHARCOAL:
                    event.setBurnTime(95);
                    furnace.setCookSpeedMultiplier(1);
                    break;
                case COAL_BLOCK:
                case LAVA_BUCKET:
                    furnace.setCookSpeedMultiplier(5);
                    break;
                default:
                    furnace.setCookSpeedMultiplier(0.5);
            }
            furnace.update();
            ParticleEffectsManager.getInstance().enhancedBurn(furnace.getBlock(),event.getBurnTime());
        }
    }

    @EventHandler
    public void onBurnEvent(FurnaceExtractEvent event) {
        if (EFManager.getInstance().isEF(event.getBlock().getLocation().toCenterLocation())) {
            event.setExpToDrop(event.getExpToDrop() * 5);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (EFManager.getInstance().removeEF(event.getBlock().getLocation().toCenterLocation())) {
            Bukkit.getConsoleSender().sendMessage("[EnF] Enhanced furnace removed at : "
                    + ChatColor.YELLOW + event.getBlock().getLocation().toCenterLocation().toVector()
                    + ChatColor.RESET + " by " + ChatColor.YELLOW + event.getPlayer().getName());
        }
    }

}
