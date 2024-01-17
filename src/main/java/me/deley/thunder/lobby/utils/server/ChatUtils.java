package me.deley.thunder.lobby.utils.server;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatUtils {
    public static void clearChat() {
        for(int i = 0; i < 50; i++)
            Bukkit.broadcast(Component.empty());
    }

    public static void clearChat(Player player) {
        for(int i = 0; i < 50; i++)
            player.sendMessage(Component.empty());
    }
}
