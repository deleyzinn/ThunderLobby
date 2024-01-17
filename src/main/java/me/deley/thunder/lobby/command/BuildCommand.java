package me.deley.thunder.lobby.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.deley.thunder.lobby.player.ThunderPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandAlias("build")
@CommandPermission("thunder.build")
public class BuildCommand extends BaseCommand {

    @Default
    @Description("Active build mode.")
    @Syntax("[on, off]")
    public void onBuild(Player player, String[] args) {
        ThunderPlayer thunderPlayer = ThunderPlayer.getPlayer(player.getUniqueId());
        if (args.length == 0) {

            if (thunderPlayer.isOnBuildMode()) {
                thunderPlayer.setOnBuildMode(false);
                player.sendMessage(Component.text("Você desativou o modo builder.").color(TextColor.color(NamedTextColor.RED.asHSV())));
            } else {
                player.sendMessage(Component.text("Você ativou o modo builder.").color(TextColor.color(Color.LIME.asRGB())));
                thunderPlayer.setOnBuildMode(true);
            }
        } else {
            switch (args[0]) {
                case "on" -> {
                    if (thunderPlayer.isOnBuildMode()) {
                        player.sendMessage(Component.text("Você já está no modo builder.").color(TextColor.color(NamedTextColor.RED.asHSV())));
                        break;
                    }
                    thunderPlayer.setOnBuildMode(true);
                    player.sendMessage(Component.text("Você ativou o modo builder.").color(TextColor.color(Color.LIME.asRGB())));
                }
                case "off" -> {
                    if (!thunderPlayer.isOnBuildMode()) {
                        player.sendMessage(Component.text("Você não está no modo builder.").color(TextColor.color(NamedTextColor.RED.asHSV())));
                        break;
                    }
                    thunderPlayer.setOnBuildMode(true);
                    player.sendMessage(Component.text("Você desativou o modo builder.").color(TextColor.color(NamedTextColor.RED.asHSV())));
                }
                default -> this.onHelp(player);
            }
        }
    }

    @HelpCommand
    public void onHelp(CommandSender sender) {
        sender.sendMessage(Component.text("/build [on, off]").color(TextColor.color(NamedTextColor.RED.asHSV())));
    }

}
