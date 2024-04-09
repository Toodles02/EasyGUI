package com.github.toodles02.easygui.api.inventory;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;


import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;

import org.bukkit.inventory.ItemStack;
import com.github.toodles02.easygui.api.manager.InventoryManager;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * A custom inventory util for creating bukkit inventories fast.
 */
public class VanillaInventory implements CustomInventory {

    protected final InventoryType type;
    protected final List<ItemStack> items = new ArrayList<>();
    protected final InventoryHandler handler = new InventoryHandler();
    protected Inventory inventory = null;

    protected final NamespacedKey namespace;

    protected Component title = Component.text("Default");


    /**
     * Creates a {@link VanillaInventory} of the desired {@link InventoryType} and {@link NamespacedKey}.
     *
     * @param type The {@link InventoryType} of this custom inventory.
     * @param namespace The {@link NamespacedKey} that will be used to register this object. See {@link #register()} for more info.
     */
    public VanillaInventory(InventoryType type, NamespacedKey namespace) {
        if (!type.isCreatable()) {
            throw new IllegalArgumentException("inventory type is not supported");
        }

        this.type = type;
        this.namespace = namespace;
    }

    /**
     * Sets the title of this inventory. Defaults to "Default" if not set.
     *
     * @param component The {@link Component} to be used for the title.
     * @return {@link VanillaInventory}
     */
    @Override
    public VanillaInventory setTitle(Component component) {
        this.title = component;
        return this;
    }

    /**
     * Creates an inventory with this instance's fields including the {@link InventoryType} and title {@link Component} and
     * the provided rows.
     * If the inventory type is not a chest, use {@link #create()}.
     *
     * @param rows The rows of the inventory.
     * @return {@link VanillaInventory}
     * @throws IllegalArgumentException If the inventory type is not a chest, the rows is not a multiple of 9, or the rows is greater than 54.
     */
    @Override
    public VanillaInventory create(int rows) throws IllegalArgumentException {

        if (type == InventoryType.CHEST) {

            if (rows > 0 && rows < 7) {
                inventory = Bukkit.createInventory(null, rows * 9, title);

                for (int i = 0; i < items.size(); i++) {
                    inventory.setItem(i, items.get(i));
                }

            } else {
                throw new IllegalArgumentException("invalid rows for creating inventory");
            }

        } else {
            throw new IllegalArgumentException("invalid type for creating an inventory of that rows");
        }

        return this;
    }

    /**
     * Creates an inventory with this instance's fields including the {@link InventoryType} and title {@link Component}.
     * If the inventory type is a chest, it will default to a size of 27 slots. See
     * {@link #create(int)} if you want to create an inventory with a different size.
     *
     * @return {@link VanillaInventory}
     */
    @Override
    public VanillaInventory create() {
        if (type == InventoryType.CHEST) {

            inventory = Bukkit.createInventory(null, 27, title);

            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getType() == Material.AIR) {
                    continue;
                }

                inventory.setItem(i, items.get(i));
            }


        } else {
            inventory = Bukkit.createInventory(null, type, title);
        }
        return this;
    }

    /**
     * Registers this inventory into the {@link InventoryManager}.
     *
     * @return {@link VanillaInventory}
     */
    @Override
    public VanillaInventory register() {
        InventoryManager.register(this);
        return this;
    }

    /**
     * Opens the inventory to the provided {@link Player}.
     * @param player The {@link Player} to open the inventory for.
     */
    @Override
    public void open(Player player) {
        player.openInventory(inventory);
    }

    /**
     * Closes the inventory regardless of the viewer(s).
     */
    @Override
    public void close() {
        inventory.close();
    }


    /**
     * Sets the slot at the provided index of this inventory to the provided {@link ItemStack}. See {@link ShapedInventory} for dynamic inventory shaping.
     *
     * @param index The index of the slot, starting from 0, for the {@link ItemStack} to be added at.
     * @param itemStack The {@link ItemStack} to be added.
     * @return {@link VanillaInventory}
     */
    @Override
    public VanillaInventory setSlot(int index, ItemStack itemStack) {
        items.set(index, itemStack);
        return this;
    }


    /**
     * Sets the handler for the {@link InventoryOpenEvent}.
     *
     * @param handler The handler to be set.
     * @return {@link VanillaInventory}
     */
    @Override
    public VanillaInventory onOpen(Consumer<InventoryOpenEvent> handler) {
        this.handler.setHandler(InventoryOpenEvent.class, handler);
        return this;
    }

    /**
     * Sets the handler for the {@link InventoryCloseEvent}.
     *
     * @param handler The handler to be set.
     * @return {@link VanillaInventory}
     */
    @Override
    public VanillaInventory onClose(Consumer<InventoryCloseEvent> handler) {
        this.handler.setHandler(InventoryCloseEvent.class, handler);
        return this;
    }

    /**
     * Sets the handler for the {@link InventoryClickEvent}.
     *
     * @param handler The handler to be set.
     * @return {@link VanillaInventory}
     */
    @Override
    public VanillaInventory onClick(Consumer<InventoryClickEvent> handler) {
        this.handler.setHandler(InventoryClickEvent.class, handler);
        return this;
    }

    /**
     * Sets the handler for the {@link InventoryDragEvent}.
     *
     * @param handler The handler to be set.
     * @return {@link VanillaInventory}
     */
    @Override
    public VanillaInventory onDrag(Consumer<InventoryDragEvent> handler) {
        this.handler.setHandler(InventoryDragEvent.class, handler);
        return this;
    }

    /**
     * Sets the handler for the {@link InventoryMoveItemEvent}.
     * <p>
     * Note, this event is only triggered when a hopper or other entity moves an item into this inventory.
     *
     * @param handler The handler to be set.
     * @return {@link VanillaInventory}
     */
    @Override
    public VanillaInventory onEntityMoveItem(Consumer<InventoryMoveItemEvent> handler) {
        this.handler.setHandler(InventoryMoveItemEvent.class, handler);
        return this;
    }

    /**
     * Sets the handler for the {@link InventoryCreativeEvent}.
     *
     * @param handler The handler to be set.
     * @return {@link VanillaInventory}
     */
    @Override
    public VanillaInventory onCreative(Consumer<InventoryCreativeEvent> handler) {
        this.handler.setHandler(InventoryCreativeEvent.class, handler);
        return this;
    }

    /**
     * Sets the handler for the {@link InventoryPickupItemEvent}.
     *
     * @param handler The handler to be set.
     * @return {@link VanillaInventory}
     */
    @Override
    public VanillaInventory onPickUp(Consumer<InventoryPickupItemEvent> handler) {
        this.handler.setHandler(InventoryPickupItemEvent.class, handler);
        return this;
    }


    /**
     * Returns the {@link NamespacedKey} linked to this instance.
     * @return {@link NamespacedKey}.
     */
    @Override
    public NamespacedKey getKey() {
        return namespace;
    }

    /**
     * Returns the title of this instance's inventory as a {@link Component}.
     * @return {@link Component}.
     */
    @Override
    public Component getTitle() {
        return title;
    }

    /**
     * Returns the handler of this class.
     * @return {@link InventoryHandler}.
     */
    @Override
    public InventoryHandler getHandler() {
        return handler;
    }

    /**
     * Returns the type of this instance's inventory.
     * @return {@link InventoryType}
     */
    @Override
    public InventoryType getType() {
        return type;
    }

    /**
     * Returns the items of this inventory indexed by their slot.
     * @return A {@link List} of {@link ItemStack}
     */
    @Override
    public List<ItemStack> getItems() {
        return inventory == null ? items : Arrays.asList(inventory.getContents());
    }

    /**
     * Returns the inventory of this instance.
     * @return {@link Inventory} if the inventory was created ({@link #create()}, {@link #create(int)}), null otherwise.
     */
    @Override
    public Inventory getInventory() {
        return inventory;
    }







}
