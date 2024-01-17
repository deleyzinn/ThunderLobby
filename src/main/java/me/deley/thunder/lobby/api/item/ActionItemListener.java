package me.deley.thunder.lobby.api.item;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import me.deley.thunder.lobby.LobbyMain;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.lang.reflect.Constructor;

public final class ActionItemListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onInteract(PlayerInteractEvent event) {
		if (event.getItem() == null)
			return;
		ItemStack stack = event.getItem();
		try {
			if (stack == null || stack.getType() == Material.AIR)
				throw new Exception();
			Constructor<?> caller = MinecraftReflection.getCraftItemStackClass()
					.getDeclaredConstructor(ItemStack.class);
			caller.setAccessible(true);
			ItemStack item = (ItemStack) caller.newInstance(stack);
			NbtCompound compound = (NbtCompound) NbtFactory.fromItemTag(item);
			if (!compound.containsKey("interactHandler"))
				return;
			InteractHandler handler = ActionItemStack.getHandler(compound.getInteger("interactHandler"));
			if (handler == null)
				throw new NullPointerException();
			Player player = event.getPlayer();
			Action action = event.getAction();
			event.setCancelled(!handler.onInteract(player, item, action, event.getClickedBlock()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onInteractEntity(PlayerInteractEntityEvent event) {
		if (event.getPlayer().getItemInHand() == null)
			return;
		ItemStack stack = event.getPlayer().getItemInHand();
		try {
			if (stack.getType() == Material.AIR)
				return;
			Constructor<?> caller = MinecraftReflection.getCraftItemStackClass()
					.getDeclaredConstructor(ItemStack.class);
			caller.setAccessible(true);
			ItemStack item = (ItemStack) caller.newInstance(stack);
			NbtCompound compound = (NbtCompound) NbtFactory.fromItemTag(item);
			if (!compound.containsKey("interactHandler"))
				return;
			InteractHandler handler = ActionItemStack.getHandler(compound.getInteger("interactHandler"));
			if (handler == null)
				throw new NullPointerException();
			event.setCancelled(!handler.onInteractEntity(event.getPlayer(), event.getRightClicked()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlace(BlockPlaceEvent event) {
		if (event.getItemInHand() == null)
			return;
		ItemStack stack = event.getItemInHand();
		try {
			if (stack == null || stack.getType() == Material.AIR)
				throw new Exception();
			Constructor<?> caller = MinecraftReflection.getCraftItemStackClass()
					.getDeclaredConstructor(ItemStack.class);
			caller.setAccessible(true);
			ItemStack item = (ItemStack) caller.newInstance(stack);
			NbtCompound compound = (NbtCompound) NbtFactory.fromItemTag(item);
			if (!compound.containsKey("interactHandler")) {
				return;
			}
			Block b = event.getBlock();
			int id = compound.getInteger("interactHandler");
			b.setMetadata("interactHandler", new FixedMetadataValue(LobbyMain.getInstance(), id));
			b.getDrops().clear();
			b.getDrops().add(ActionItemStack
					.setTag(new ItemStack(event.getBlock().getType(), 1, event.getBlock().getData()), id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBreak(BlockBreakEvent event) {
		Block b = event.getBlock();
		if (!b.hasMetadata("interactHandler"))
			return;
		b.getDrops().clear();
		b.getDrops()
				.add(ActionItemStack.setTag(new ItemStack(event.getBlock().getType(), 1, event.getBlock().getData()),
						b.getMetadata("interactHandler").get(0).asInt()));
	}
}
