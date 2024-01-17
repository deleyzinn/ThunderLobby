package me.deley.thunder.lobby.api.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

public class ThunderExpansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "thunderlobby";
    }

    @Override
    public @NotNull String getAuthor() {
        return "deleyy";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }
}
