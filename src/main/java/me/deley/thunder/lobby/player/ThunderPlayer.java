package me.deley.thunder.lobby.player;

import lombok.Getter;
import lombok.Setter;
import me.deley.thunder.lobby.LobbyMain;
import me.deley.thunder.lobby.api.scoreboard.FastBoard;
import me.deley.thunder.lobby.loader.ScoreboardConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@Setter
public class ThunderPlayer {
    private FastBoard board;
    private ScoreboardConstructor scoreboardConstructor;
    private UUID uniqueId;
    private boolean onBuildMode = false;
    private boolean playerVisible = true;
    private boolean flying = false;


    public ThunderPlayer(UUID uuid) {
        this.uniqueId = uuid;
    }

    public static ThunderPlayer getPlayer(UUID uuid) {
        return LobbyMain.getInstance().getController().getPlayer(uuid);
    }

    public Player getHuman() {
        return Bukkit.getPlayer(uniqueId);
    }
}
