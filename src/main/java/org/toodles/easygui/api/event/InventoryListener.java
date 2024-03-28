package org.toodles.easygui.api.event;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.toodles.easygui.api.inventory.CustomInventory;
import org.toodles.easygui.api.manager.InventoryManager;

/**
 * The listener for all registered {@link CustomInventory}.
 */
public class InventoryListener implements Listener {

    /**
     * Handles the {@link InventoryOpenEvent}.
     * @param event The {@link InventoryOpenEvent}.
     */
    @EventHandler
    public void onOpen(InventoryOpenEvent event) {

        for (CustomInventory customInventory : InventoryManager.getInventories()) {
            if (customInventory.getInventory().equals(event.getInventory())) {
                customInventory.getHandler().handle(event);
            }
        }

    }

    /**
     * Handles the {@link InventoryCloseEvent}.
     * @param event The {@link InventoryCloseEvent}.
     */
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        for (CustomInventory customInventory : InventoryManager.getInventories()) {
            if (customInventory.getInventory().equals(event.getInventory())) {
                customInventory.getHandler().handle(event);
            }
        }

    }

    /**
     * Handles the {@link InventoryDragEvent}.
     * @param event The {@link InventoryDragEvent}.
     */
    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        for (CustomInventory customInventory : InventoryManager.getInventories()) {
            if (customInventory.getInventory().equals(event.getInventory())) {
                customInventory.getHandler().handle(event);
            }
        }

    }

    /**
     * Handles the {@link InventoryClickEvent}.
     * @param event The {@link InventoryClickEvent}.
     */
    @EventHandler
    public void onClick(InventoryClickEvent event) {

        for (CustomInventory customInventory : InventoryManager.getInventories()) {
            if (customInventory.getInventory().equals(event.getInventory()) || customInventory.getInventory().equals(event.getClickedInventory())) {
                customInventory.getHandler().handle(event);
            }
        }

    }

    /**
     * Handles the {@link InventoryPickupItemEvent}.
     * @param event The {@link InventoryPickupItemEvent}.
     */
    @EventHandler
    public void onPickUp(InventoryPickupItemEvent event) {

        for (CustomInventory customInventory : InventoryManager.getInventories()) {
            if (customInventory.getInventory().equals(event.getInventory())) {
                customInventory.getHandler().handle(event);
            }
        }

    }

    /**
     * Handles the {@link InventoryMoveItemEvent}.
     * @param event The {@link InventoryMoveItemEvent}.
     */
    @EventHandler
    public void onEntityMoveItem(InventoryMoveItemEvent event) {

        for (CustomInventory customInventory : InventoryManager.getInventories()) {
            Inventory inventory = customInventory.getInventory();
            if (inventory.equals(event.getDestination()) || inventory.equals(event.getInitiator())) {
                customInventory.getHandler().handle(event);
            }
        }

    }

    /**
     * Handles the {@link InventoryCreativeEvent}.
     * @param event The {@link InventoryCreativeEvent}.
     */
    @EventHandler
    public void onCreative(InventoryCreativeEvent event) {

        for (CustomInventory customInventory : InventoryManager.getInventories()) {
            if (customInventory.getInventory().equals(event.getInventory()) || customInventory.getInventory().equals(event.getClickedInventory())) {
                customInventory.getHandler().handle(event);
            }
        }

    }



}
