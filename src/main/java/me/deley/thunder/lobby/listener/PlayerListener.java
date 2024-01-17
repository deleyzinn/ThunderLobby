package me.deley.thunder.lobby.listener;

import me.deley.thunder.lobby.LobbyMain;
import me.deley.thunder.lobby.api.scoreboard.FastBoard;
import me.deley.thunder.lobby.loader.ScoreboardConstructor;
import me.deley.thunder.lobby.player.ThunderPlayer;
import me.deley.thunder.lobby.utils.inventory.Inventories;
import me.deley.thunder.lobby.utils.server.ChatUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerShowEntityEvent;
import org.bukkit.inventory.ItemStack;


public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        LobbyMain.getInstance().getBoardMap().remove(event.getPlayer().getUniqueId());
        event.joinMessage(null);
        Player player = event.getPlayer();
        player.showTitle(Title.title(
                Component.text("ThunderLobby").color(TextColor.color(Color.AQUA.asRGB())),
                Component.text("Um sistema de lobby gratuito.").color(TextColor.color(Color.WHITE.asRGB()))
        ));
        player.getInventory().clear();
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 0.5f);
        ThunderPlayer thunderPlayer = ThunderPlayer.getPlayer(player.getUniqueId());
        ChatUtils.clearChat(player);
        Inventories.joinItems(player);
        ScoreboardConstructor constructor = new ScoreboardConstructor(LobbyMain.getInstance().getScoreboardLoader().getLinesList(), Component.text(ChatColor.translateAlternateColorCodes('&', LobbyMain.getInstance().getScoreConfig().getConfig().getString("scoreboard.title.text"))));
        thunderPlayer.setScoreboardConstructor(constructor);
        constructor.handleScore(player);
        LobbyMain.getInstance().getBoardMap().put(player.getUniqueId(), thunderPlayer.getBoard());
    }

    @EventHandler
    public void asyncJoin(PlayerLoginEvent event) {
        LobbyMain.getInstance().getController().loadPlayer(new ThunderPlayer(event.getPlayer().getUniqueId()));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        event.setCancelled(!(ThunderPlayer.getPlayer(event.getPlayer().getUniqueId()).isOnBuildMode()));
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        event.setCancelled(!(ThunderPlayer.getPlayer(event.getPlayer().getUniqueId()).isOnBuildMode()));
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        ThunderPlayer player = ThunderPlayer.getPlayer(event.getPlayer().getUniqueId());
        if (player.getBoard() != null) {
            LobbyMain.getInstance().getBoardMap().remove(player.getUniqueId());
            player.getBoard().delete();
        }
        LobbyMain.getInstance().getController().unloadPlayer(event.getPlayer().getUniqueId());

    }

    @EventHandler
    public void showEvent(PlayerShowEntityEvent event) {
    }
}
