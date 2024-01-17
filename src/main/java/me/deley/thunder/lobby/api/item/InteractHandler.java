package me.deley.thunder.lobby.api.item;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public interface InteractHandler {

    boolean onInteract(Player player, ItemStack item, Action action, Block clicked);

    default boolean onInteractEntity(Player player, Entity rightClicked) {
        return false;
    }
}
