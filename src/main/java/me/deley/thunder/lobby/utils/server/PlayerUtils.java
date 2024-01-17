package me.deley.thunder.lobby.utils.server;

import me.deley.thunder.lobby.LobbyMain;
import me.deley.thunder.lobby.player.ThunderPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerUtils {

    public static void handleVisibility(Player player) {
        ThunderPlayer thunderPlayer = ThunderPlayer.getPlayer(player.getUniqueId());

        if (thunderPlayer.isPlayerVisible())
            Bukkit.getOnlinePlayers().forEach(player1 -> player.showPlayer(LobbyMain.getInstance(), player1));
        else Bukkit.getOnlinePlayers().forEach(player1 -> player.hidePlayer(LobbyMain.getInstance(), player1));
    }


}
