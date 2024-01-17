package me.deley.thunder.lobby.utils.inventory;

import me.deley.thunder.lobby.api.item.ItemBuilder;
import me.deley.thunder.lobby.player.ThunderPlayer;
import me.deley.thunder.lobby.utils.server.PlayerUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Inventories {

    public static void joinItems(Player player) {
        if (player == null)
            return;
        ThunderPlayer thunderPlayer = ThunderPlayer.getPlayer(player.getUniqueId());
        PlayerInventory inventory = player.getInventory();
        inventory.clear();
        inventory.setItem(0, new ItemBuilder(Material.COMPASS).setName("&7Seletor de Jogos").getStack());
        inventory.setItem(8, new ItemBuilder(((thunderPlayer.isPlayerVisible()) ? Material.LIME_DYE : Material.GRAY_DYE)).setName("&7Jogadores " + ((thunderPlayer.isPlayerVisible()) ? "&7(&aON&7)" : "&7(&cOFF&7)")).getStack((player1, item, action, clicked) -> {
            ThunderPlayer.getPlayer(player1.getUniqueId()).setPlayerVisible(!thunderPlayer.isPlayerVisible());
            PlayerUtils.handleVisibility(player1);
            joinItems(player);
            return false;
        }));
        player.updateInventory();
    }
}
