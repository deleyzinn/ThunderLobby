package me.deley.thunder.lobby.loader;

import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import me.deley.thunder.lobby.LobbyMain;
import me.deley.thunder.lobby.listener.ScoreboardListener;
import me.deley.thunder.lobby.utils.configuration.ConfigUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
public class ScoreboardLoader {
    @Getter
    private List<String> linesList = new LinkedList<>();

    public ScoreboardLoader() {
        init();
    }

    public void init() {
        ConfigUtils configUtils = LobbyMain.getInstance().getScoreConfig();
        for (String lines : configUtils.getConfig().getStringList("scoreboard.lines")) {
            linesList.add(ChatColor.translateAlternateColorCodes('&', lines));
        }
    }

}
