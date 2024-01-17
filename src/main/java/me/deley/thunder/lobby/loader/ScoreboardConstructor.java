package me.deley.thunder.lobby.loader;

import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import me.deley.thunder.lobby.LobbyMain;
import me.deley.thunder.lobby.api.scoreboard.FastBoard;
import me.deley.thunder.lobby.player.ThunderPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public record ScoreboardConstructor(List<String> lines, Component title) {
    private static final List<Component> convertedLines = new LinkedList<>();

    public void handleScore(Player player) {
        this.convertLines(lines, player);
        ThunderPlayer thunderPlayer = ThunderPlayer.getPlayer(player.getUniqueId());
        thunderPlayer.setBoard(new FastBoard(player));
        FastBoard board = thunderPlayer.getBoard();
        board.updateTitle(title);
        board.updateLines(convertedLines);
    }

    public void convertLines(List<String> lines, Player player) {
        convertedLines.clear();
        for (String line : lines) {
            if (LobbyMain.getInstance().isPlaceholder()) line = PlaceholderAPI.setPlaceholders(player, line);
            convertedLines.add(Component.text(ChatColor.translateAlternateColorCodes('&', line)));
        }
    }


}
