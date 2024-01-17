package me.deley.thunder.lobby.player;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

public class PlayerController {

    private HashMap<UUID, ThunderPlayer> cachedPlayers = new LinkedHashMap<>();

    public void loadPlayer(ThunderPlayer thunderPlayer) {
        this.cachedPlayers.put(thunderPlayer.getUniqueId(), thunderPlayer);
    }

    public void unloadPlayer(UUID uuid) {
        this.cachedPlayers.remove(uuid);
    }

    public ThunderPlayer getPlayer(UUID uuid) {
        return cachedPlayers.get(uuid);
    }
}
