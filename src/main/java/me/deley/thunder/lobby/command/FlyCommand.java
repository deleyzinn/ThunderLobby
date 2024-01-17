package me.deley.thunder.lobby.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.deley.thunder.lobby.player.ThunderPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

@CommandPermission("thunder.fly")
@CommandAlias("fly|voar|voo")
public class FlyCommand extends BaseCommand {
    @Default@Description("Toggle flying mode.")@Syntax("[on, off]")
    public void onFly(Player player, String[] args) {
        ThunderPlayer thunderPlayer = ThunderPlayer.getPlayer(player.getUniqueId());
        if (thunderPlayer.isFlying()) {
            player.sendMessage(Component.text("Você desativou o modo fly.").color(TextColor.color(NamedTextColor.RED)));
            thunderPlayer.setFlying(false);
            player.setAllowFlight(false);
            player.setFlying(false);
        } else {
            player.sendMessage(Component.text("Você ativou o modo fly.").color(TextColor.color(NamedTextColor.GREEN)));
            thunderPlayer.setFlying(true);
            player.setAllowFlight(true);
            player.setFlying(true);
        }
    }

    @HelpCommand
    public void onHelp(Player player) {
        player.sendMessage(Component.text("/fly [on, off]").color(TextColor.color(NamedTextColor.RED)));
    }
}
