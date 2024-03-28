package org.toodles.easygui.api.inventory;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;


import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.toodles.easygui.api.manager.InventoryManager;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A custom inventory util for creating bukkit inventories fast.
 */
public class CustomInventory {

    private final InventoryType type;
    private final List<ItemStack> items = new ArrayList<>();
    private final InventoryHandler handler = new InventoryHandler();
    private Inventory inventory = null;

    private final NamespacedKey namespace;

    private Component title = Component.text("Default");

    private InventoryShape shape = null;


    /**
     * Creates a {@link CustomInventory} of the desired {@link InventoryType} and {@link NamespacedKey}.
     *
     * @param type The {@link InventoryType} of this custom inventory.
     * @param namespace The {@link NamespacedKey} that will be used to register this object. See {@link #register()} for more info.
     */
    public CustomInventory(InventoryType type, NamespacedKey namespace) {
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
     * @return {@link CustomInventory}
     */
    public CustomInventory setTitle(Component component) {
        this.title = component;
        return this;
    }

    /**
     * Creates an inventory with this instance's fields including the {@link InventoryType} and title {@link Component} and
     * the provided size.
     * If the inventory type is not a chest, use {@link #create()}.
     *
     * @param size The size of the inventory.
     * @return {@link CustomInventory}
     * @throws IllegalArgumentException If the inventory type is not a chest, the size is not a multiple of 9, or the size is greater than 54.
     */
    public CustomInventory create(int size) throws IllegalArgumentException {

        if (type == InventoryType.CHEST) {

            initializeShape();

            if (size >= 54 && size % 9 == 0) {
                inventory = Bukkit.createInventory(null, size, title);

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).getType() == Material.AIR) {
                        continue;
                    }

                    inventory.setItem(i, items.get(i));
                }

            } else {
                throw new IllegalArgumentException("invalid size for creating inventory");
            }

        } else {
            throw new IllegalArgumentException("invalid type for creating an inventory of that size");
        }

        return this;
    }

    /**
     * Creates an inventory with this instance's fields including the {@link InventoryType} and title {@link Component}.
     * If the inventory type is a chest, it will default to a size of 27 slots. See
     * {@link #create(int)} if you want to create an inventory with a different size.
     *
     * @return {@link CustomInventory}
     */
    public CustomInventory create() {
        if (type == InventoryType.CHEST) {

            initializeShape();

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
     * @return {@link CustomInventory}
     */
    public CustomInventory register() {
        InventoryManager.register(this);
        return this;
    }
    private void initializeShape() {
        if (shape != null) {
            Map<Character, ItemStack> shapeMap = shape.getShapeMap();
            for (int i = 0; i < shape.getShape().length(); i++) {
                if (shapeMap.get(shape.getShape().charAt(i)) == null) {
                    throw new IllegalStateException("character is not set in shape");
                }
                items.add(i, shapeMap.get(shape.getShape().charAt(i)));
            }
        } else {
            throw new IllegalStateException("shape is null");
        }
    }

    /**
     * Opens the inventory to the provided {@link Player}.
     * @param player The {@link Player} to open the inventory for.
     */
    public void open(Player player) {
        player.openInventory(inventory);
    }

    /**
     * Closes the inventory regardless of the viewer(s).
     */
    public void close() {
        inventory.close();
    }


    /**
     * Sets the slot at the provided index of this inventory to the provided {@link ItemStack}. See {@link #setShape(InventoryShape)} for dynamic inventory shaping.
     *
     * @param index The index of the slot, starting from 0, for the {@link ItemStack} to be added at.
     * @param itemStack The {@link ItemStack} to be added.
     * @return {@link CustomInventory}
     */
    public CustomInventory setSlot(int index, ItemStack itemStack) {
        items.set(index, itemStack);
        return this;
    }


    /**
     * Sets the shape of this inventory.
     *
     * @param shape The {@link InventoryShape} to be used.
     * @return {@link CustomInventory}
     * @throws IllegalStateException If this inventory's type is not a chest inventory.
     */
    public CustomInventory setShape(InventoryShape shape) throws IllegalStateException {
        if (type != InventoryType.CHEST) {
            throw new IllegalStateException("can't shape this inventory type");
        }

        this.shape = shape;
        return this;
    }

    /**
     * Sets the handler for the {@link InventoryOpenEvent}.
     *
     * @param handler The handler to be set.
     * @return {@link CustomInventory}
     */
    public CustomInventory onOpen(Consumer<InventoryOpenEvent> handler) {
        this.handler.setHandler(InventoryOpenEvent.class, handler);
        return this;
    }

    /**
     * Sets the handler for the {@link InventoryCloseEvent}.
     *
     * @param handler The handler to be set.
     * @return {@link CustomInventory}
     */
    public CustomInventory onClose(Consumer<InventoryCloseEvent> handler) {
        this.handler.setHandler(InventoryCloseEvent.class, handler);
        return this;
    }

    /**
     * Sets the handler for the {@link InventoryClickEvent}.
     *
     * @param handler The handler to be set.
     * @return {@link CustomInventory}
     */
    public CustomInventory onClick(Consumer<InventoryClickEvent> handler) {
        this.handler.setHandler(InventoryClickEvent.class, handler);
        return this;
    }

    /**
     * Sets the handler for the {@link InventoryDragEvent}.
     *
     * @param handler The handler to be set.
     * @return {@link CustomInventory}
     */
    public CustomInventory onDrag(Consumer<InventoryDragEvent> handler) {
        this.handler.setHandler(InventoryDragEvent.class, handler);
        return this;
    }

    /**
     * Sets the handler for the {@link InventoryMoveItemEvent}.
     * <p>
     * Note, this event is only triggered when a hopper or other entity moves an item into this inventory.
     *
     * @param handler The handler to be set.
     * @return {@link CustomInventory}
     */
    public CustomInventory onEntityMoveItem(Consumer<InventoryMoveItemEvent> handler) {
        this.handler.setHandler(InventoryMoveItemEvent.class, handler);
        return this;
    }

    /**
     * Sets the handler for the {@link InventoryCreativeEvent}.
     *
     * @param handler The handler to be set.
     * @return {@link CustomInventory}
     */
    public CustomInventory onCreative(Consumer<InventoryCreativeEvent> handler) {
        this.handler.setHandler(InventoryCreativeEvent.class, handler);
        return this;
    }

    /**
     * Sets the handler for the {@link InventoryPickupItemEvent}.
     *
     * @param handler The handler to be set.
     * @return {@link CustomInventory}
     */
    public CustomInventory onPickUp(Consumer<InventoryPickupItemEvent> handler) {
        this.handler.setHandler(InventoryPickupItemEvent.class, handler);
        return this;
    }

    /**
     * Returns the {@link InventoryShape}.
     * @return {@link InventoryShape}.
     */
    public InventoryShape getShape() {
        return shape;
    }

    /**
     * Returns the {@link NamespacedKey} linked to this instance.
     * @return {@link NamespacedKey}.
     */
    public NamespacedKey getKey() {
        return namespace;
    }

    /**
     * Returns the title of this instance's inventory as a {@link Component}.
     * @return {@link Component}.
     */
    public Component getTitle() {
        return title;
    }

    /**
     * Returns the handler of this class.
     * @return {@link InventoryHandler}.
     */
    public InventoryHandler getHandler() {
        return handler;
    }

    /**
     * Returns the type of this instance's inventory.
     * @return {@link InventoryType}
     */
    public InventoryType getType() {
        return type;
    }

    /**
     * Returns the items of this inventory indexed by their slot.
     * @return A {@link List} of {@link ItemStack}
     */
    public List<ItemStack> getItems() {
        return inventory == null ? items : Arrays.asList(inventory.getContents());
    }

    /**
     * Returns the inventory of this instance.
     * @return {@link Inventory} if the inventory was created ({@link #create()}, {@link #create(int)}), null otherwise.
     */
    public Inventory getInventory() {
        return inventory;
    }







}
