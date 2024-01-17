package me.deley.thunder.lobby.listener;

import io.papermc.paper.event.player.PlayerPickItemEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public class ItemListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!event.hasItem())
            return;
        ItemStack itemStack = event.getItem();

    }

    @EventHandler
    public void itemPickup(PlayerAttemptPickupItemEvent event) {
        event.setCancelled(true);
    }
}
