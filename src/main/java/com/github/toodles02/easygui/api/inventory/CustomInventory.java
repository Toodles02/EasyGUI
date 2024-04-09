package com.github.toodles02.easygui.api.inventory;

import com.github.toodles02.easygui.api.manager.InventoryManager;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Consumer;

public interface CustomInventory {

    /**
     * Sets the title of this inventory. Defaults to "Default" if not set.
     *
     * @param component The {@link Component} to be used for the title.
     * @return {@link VanillaInventory}
     */
    CustomInventory setTitle(Component component) ;

    /**
     * Creates an inventory with this instance's fields including the {@link InventoryType} and title {@link Component} and
     * the provided rows.
     * If the inventory type is not a chest, use {@link #create()}.
     *
     * @param rows The rows of the inventory.
     * @return {@link VanillaInventory}
     * @throws IllegalArgumentException If the inventory type is not a chest, the rows is not a multiple of 9, or the rows is greater than 54.
     */
    CustomInventory create(int rows) throws IllegalArgumentException;

    /**
     * Creates an inventory with this instance's fields including the {@link InventoryType} and title {@link Component}.
     * If the inventory type is a chest, it will default to a size of 27 slots. See
     * {@link #create(int)} if you want to create an inventory with a different size.
     *
     * @return {@link VanillaInventory}
     */
    CustomInventory create();

    /**
     * Registers this inventory into the {@link InventoryManager}.
     *
     * @return {@link VanillaInventory}
     */
    default CustomInventory register() {
        InventoryManager.register(this);
        return this;
    }

    /**
     * Opens the inventory to the provided {@link Player}.
     * @param player The {@link Player} to open the inventory for.
     */
    void open(Player player);

    /**
     * Closes the inventory regardless of the viewer(s).
     */
    void close();


    /**
     * Sets the slot at the provided index of this inventory to the provided {@link ItemStack}. See {@link VanillaInventory} for dynamic inventory shaping.
     *
     * @param index The index of the slot, starting from 0, for the {@link ItemStack} to be added at.
     * @param itemStack The {@link ItemStack} to be added.
     * @return {@link VanillaInventory}
     */
    CustomInventory setSlot(int index, ItemStack itemStack);

    /**
     * Sets the handler for the {@link InventoryOpenEvent}.
     *
     * @param handler The handler to be set.
     * @return {@link VanillaInventory}
     */
    CustomInventory onOpen(Consumer<InventoryOpenEvent> handler);

    /**
     * Sets the handler for the {@link InventoryCloseEvent}.
     *
     * @param handler The handler to be set.
     * @return {@link VanillaInventory}
     */
    CustomInventory onClose(Consumer<InventoryCloseEvent> handler);

    /**
     * Sets the handler for the {@link InventoryClickEvent}.
     *
     * @param handler The handler to be set.
     * @return {@link VanillaInventory}
     */
    CustomInventory onClick(Consumer<InventoryClickEvent> handler);

    /**
     * Sets the handler for the {@link InventoryDragEvent}.
     *
     * @param handler The handler to be set.
     * @return {@link VanillaInventory}
     */
    CustomInventory onDrag(Consumer<InventoryDragEvent> handler);

    /**
     * Sets the handler for the {@link InventoryMoveItemEvent}.
     * <p>
     * Note, this event is only triggered when a hopper or other entity moves an item into this inventory.
     *
     * @param handler The handler to be set.
     * @return {@link VanillaInventory}
     */
    CustomInventory onEntityMoveItem(Consumer<InventoryMoveItemEvent> handler);

    /**
     * Sets the handler for the {@link InventoryCreativeEvent}.
     *
     * @param handler The handler to be set.
     * @return {@link VanillaInventory}
     */
    CustomInventory onCreative(Consumer<InventoryCreativeEvent> handler);

    /**
     * Sets the handler for the {@link InventoryPickupItemEvent}.
     *
     * @param handler The handler to be set.
     * @return {@link VanillaInventory}
     */
    CustomInventory onPickUp(Consumer<InventoryPickupItemEvent> handler);


    /**
     * Returns the {@link NamespacedKey} linked to this instance.
     * @return {@link NamespacedKey}.
     */
    NamespacedKey getKey();

    /**
     * Returns the title of this instance's inventory as a {@link Component}.
     * @return {@link Component}.
     */
    Component getTitle();

    /**
     * Returns the handler of this class.
     * @return {@link InventoryHandler}.
     */
    InventoryHandler getHandler();

    /**
     * Returns the type of this instance's inventory.
     * @return {@link InventoryType}
     */
    InventoryType getType();

    /**
     * Returns the items of this inventory indexed by their slot.
     * @return A {@link List} of {@link ItemStack}
     */
    List<ItemStack> getItems();

    /**
     * Returns the inventory of this instance.
     * @return {@link Inventory} if the inventory was created ({@link #create()}, {@link #create(int)}), null otherwise.
     */
    Inventory getInventory();
}