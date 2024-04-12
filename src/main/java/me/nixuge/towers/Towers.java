package me.nixuge.towers;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import lombok.val;
import me.nixuge.towers.game.GameManager;
import me.nixuge.towers.listeners.EnchantItemListener;
import me.nixuge.towers.listeners.EntityDamageByEntityListener;
import me.nixuge.towers.listeners.EntityExplodeListener;
import me.nixuge.towers.listeners.FoodLevelChangeListener;
import me.nixuge.towers.listeners.InventoryListener;
import me.nixuge.towers.listeners.PlayerDeathListener;
import me.nixuge.towers.listeners.PlayerDropItemListener;
import me.nixuge.towers.listeners.PlayerInteractAtEntityListener;
import me.nixuge.towers.listeners.PlayerJoinListener;
import me.nixuge.towers.listeners.PlayerMoveListener;
import me.nixuge.towers.listeners.PlayerQuitListener;
import me.nixuge.towers.listeners.PlayerRespawnListener;
import me.nixuge.towers.player.PlayersManager;

public class Towers extends JavaPlugin {
    private static final List<Listener> listeners = Arrays.asList(
        new EnchantItemListener(), new EntityDamageByEntityListener(),
        new EntityExplodeListener(), new FoodLevelChangeListener(),
        new InventoryListener(), new PlayerDeathListener(),
        new PlayerDropItemListener(), new PlayerInteractAtEntityListener(),
        new PlayerJoinListener(), new PlayerMoveListener(),
        new PlayerQuitListener(), new PlayerRespawnListener()
    );

    @Getter
    private static Towers instance;
    @Getter
    private static GameManager gameManager;

    @Override
    public void onEnable() {
        instance = this;
        
        gameManager = new GameManager(this);
        gameManager.startGame();

        // Only for debugging purposes after a /rl - should ideally be removed.
        Bukkit.getOnlinePlayers().forEach(p -> {
            val towersP = PlayersManager.addPlayerRandomTeam(p);
        });

        PluginManager pluginManager = Bukkit.getPluginManager();
        listeners.forEach(listener -> pluginManager.registerEvents(listener, this));
    }

    @Override
    public void onDisable() {

    }
}