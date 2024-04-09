package com.github.toodles02.easygui;


import com.github.toodles02.easygui.api.event.InventoryListener;
import com.github.toodles02.easygui.api.inventory.InventoryShape;
import com.github.toodles02.easygui.api.inventory.ShapedInventory;
import com.github.toodles02.easygui.api.inventory.VanillaInventory;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The plugin class {@link EasyGUI}.
 */
public final class EasyGUI extends JavaPlugin {

    private static ShapedInventory testMenu;

    /**
     * Handles setup configurations when the server is enabled.
     */
    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getLogger().info("Registered listener!");
    }

    /**
     * Returns this server instance.
     * @return {@link EasyGUI}.
     */
    public static EasyGUI getInstance() {
        return JavaPlugin.getPlugin(EasyGUI.class);
    }
}
