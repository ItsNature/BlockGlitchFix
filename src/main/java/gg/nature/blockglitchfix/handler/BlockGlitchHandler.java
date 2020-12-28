package gg.nature.blockglitchfix.handler;

import gg.nature.blockglitchfix.BlockGlitchFix;
import gg.nature.blockglitchfix.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BlockGlitchHandler implements Listener {

    private final Map<UUID, Long> cooldowns;

    public BlockGlitchHandler() {
        this.cooldowns = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(this, BlockGlitchFix.getInstance());
    }

    public void disable() {
        this.cooldowns.clear();
    }

    private void check(Player player, Cancellable event) {
        if (this.cooldowns.containsKey(player.getUniqueId()) && System.currentTimeMillis() - this.cooldowns.get(player.getUniqueId()) < 500L) {
            event.setCancelled(true);
            player.sendMessage(Config.DENY_MESSAGE);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.isCancelled()) return;

        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) return;

        Block block = event.getBlock();
        if (block.getType().isSolid()) {
            this.cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
        }

        Location location = player.getLocation().clone();
        if (!Config.PHASE_TYPE_ENABLED || location.getBlock().getType() == Material.AIR) return;

        location.setX(location.getBlockX() + 0.5D);
        location.setZ(location.getBlockZ() + 0.5D);

        player.teleport(location);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!Config.ENTITY_TYPE_ENABLED || !(event.getDamager() instanceof Player)) return;

        this.check((Player) event.getDamager(), event);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!Config.SIGN_TYPE_ENABLED || event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block block = event.getClickedBlock();
        if (block == null || (block.getType() != Material.SIGN_POST && block.getType() != Material.WALL_SIGN)) return;

        this.check(event.getPlayer(), event);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onVehicleEnter(VehicleEnterEvent event) {
        if (!Config.MINECART_TYPE_ENABLED || !(event.getEntered() instanceof Player) || !(event.getVehicle() instanceof Minecart)) return;

        this.check((Player) event.getEntered(), event);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.cooldowns.remove(event.getPlayer().getUniqueId());
    }
}
