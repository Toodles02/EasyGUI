package org.toodles.easygui;


import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.toodles.easygui.api.event.InventoryListener;

/**
 * The plugin class {@link EasyGUI}.
 */
public final class EasyGUI extends JavaPlugin {

    /**
     * Handles setup configurations when the server is enabled.
     */
    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getLogger().info("Registered listener!");


    }

    /**
     * Returns the server instance of {@link EasyGUI}.
     * @return {@link EasyGUI}.
     */
    public static EasyGUI getInstance() {
        return JavaPlugin.getPlugin(EasyGUI.class);
    }
}
