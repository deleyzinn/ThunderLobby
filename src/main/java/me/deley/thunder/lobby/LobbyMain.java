package me.deley.thunder.lobby;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.Locales;
import co.aikar.commands.MessageType;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import me.deley.thunder.lobby.api.item.ActionItemListener;
import me.deley.thunder.lobby.api.placeholder.ThunderExpansion;
import me.deley.thunder.lobby.api.scoreboard.FastBoard;
import me.deley.thunder.lobby.command.BuildCommand;
import me.deley.thunder.lobby.command.FlyCommand;
import me.deley.thunder.lobby.listener.ItemListener;
import me.deley.thunder.lobby.listener.PlayerListener;
import me.deley.thunder.lobby.loader.ScoreboardLoader;
import me.deley.thunder.lobby.player.PlayerController;
import me.deley.thunder.lobby.player.ThunderPlayer;
import me.deley.thunder.lobby.utils.configuration.ConfigUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class LobbyMain extends JavaPlugin {
    private final Map<UUID, FastBoard> boardMap = new HashMap<>();
    @Getter
    public static LobbyMain instance;
    private PlayerController controller;
    private ConfigUtils scoreConfig;
    private ScoreboardLoader scoreboardLoader;
    private boolean isPlaceholder;

    @Override
    public void onLoad() {
        instance = this;
        super.onLoad();
        scoreConfig = new ConfigUtils(LobbyMain.getInstance().getDataFolder(), "scoreboard.yml");
        scoreboardLoader = new ScoreboardLoader();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {


        controller = new PlayerController();
        registerListeners(new PlayerListener(), new ItemListener(),new ActionItemListener());
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new BuildCommand());
        manager.registerCommand(new FlyCommand());
        manager.addSupportedLanguage(Locales.PORTUGUESE);
        manager.getLocales().setDefaultLocale(Locales.PORTUGUESE);
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("Loading PlaceholderAPI injector.");
            new ThunderExpansion().register();
            isPlaceholder = true;
            //....//
        } else {
            isPlaceholder = false;
            getLogger().warning("Where is PlaceholderAPI? This plugin maybe be util...");
        }

        getServer().getScheduler().runTaskTimer(this, () -> Bukkit.getOnlinePlayers().forEach(player ->
                ThunderPlayer.getPlayer(player.getUniqueId()).getScoreboardConstructor().handleScore(player)), 0, 20);
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners)
            Bukkit.getPluginManager().registerEvents(listener, this);
    }

}
