package me.deley.thunder.lobby.api.item;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ItemBuilder {
    private final ItemStack itemStack;
    private boolean glow = false;


    public ItemBuilder(Material type) {
        this(new ItemStack(type));
    }

    private ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder with(Consumer<ItemStack> consumer) {
        consumer.accept(this.itemStack);
        return this;
    }

    public ItemBuilder withMeta(Consumer<ItemMeta> consumer) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        consumer.accept(itemMeta);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        return with(item -> item.setAmount(amount));
    }


    public ItemBuilder setType(Material type) {
        return with(item -> item.setType(type));
    }

    public ItemBuilder unbreakable() {
        return withMeta(itemMeta -> itemMeta.setUnbreakable(true));
    }
    public ItemBuilder glow() {
        glow = true;
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        return with(item -> item.addEnchantment(enchantment, level));
    }

    public ItemBuilder addEnchantment(Map<Enchantment, Integer> enchantments) {
        return with(item -> item.addEnchantments(enchantments));
    }

    public ItemBuilder addUnsafeEnchantments(Enchantment enchantment, int level) {
        return with(item -> item.addUnsafeEnchantment(enchantment, level));
    }

    public ItemBuilder addUnsafeEnchantments(Map<Enchantment, Integer> enchantments) {
        return with(item -> item.addUnsafeEnchantments(enchantments));
    }

    public ItemBuilder setName(String displayName) {
        return withMeta(meta -> meta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', displayName))));
    }

    public ItemBuilder setLore(List<String> lore) {
        return withMeta(meta -> meta.setLore(lore));
    }

    public ItemBuilder addItemFlags(ItemFlag... itemFlags) {
        return withMeta(meta -> meta.addItemFlags(itemFlags));
    }

    public ItemBuilder removeItemFlags(ItemFlag... itemFlags) {
        return withMeta(meta -> meta.addItemFlags(itemFlags));
    }

    public ItemBuilder hideEnchantments() {
        return addItemFlags(ItemFlag.HIDE_ENCHANTS);
    }

    public ItemBuilder hideAttributes() {
        return addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    }


    public ItemStack getStack() {
        if (glow) {
            itemStack.getEnchantments();
            if (itemStack.getEnchantments().isEmpty()) {
                try {
                    Constructor<?> caller = MinecraftReflection.getCraftItemStackClass().getDeclaredConstructor(ItemStack.class);

                    caller.setAccessible(true);

                    ItemStack item = (ItemStack) caller.newInstance(itemStack);
                    NbtCompound compound = (NbtCompound) NbtFactory.fromItemTag(item);

                    compound.put(NbtFactory.ofList("ench"));
                    return item;
                } catch (Exception ex) {
                    ex.fillInStackTrace();
                }
            }
        }


        return this.itemStack;
    }

    public ItemStack getStack(InteractHandler interactHandler) {
        return ActionItemStack.setTag(getStack(), ActionItemStack.register(interactHandler));
    }

    public static ItemBuilder fromItem(ItemStack item) {
        return new ItemBuilder(item);
    }

}
