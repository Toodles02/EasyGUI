package com.github.toodles02.easygui.api.inventory;

import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ShapedInventory extends VanillaInventory {


    protected InventoryShape shape = null;

    /**
     * Creates a {@link VanillaInventory} of the desired {@link InventoryType} and {@link NamespacedKey}.
     *
     * @param type      The {@link InventoryType} of this custom inventory.
     * @param namespace The {@link NamespacedKey} that will be used to register this object. See {@link #register()} for more info.
     */
    public ShapedInventory(InventoryType type, NamespacedKey namespace) {
        super(type, namespace);
    }


    /**
     * Returns the {@link InventoryShape}.
     * @return {@link InventoryShape}.
     */
    public InventoryShape getShape() {
        return shape;
    }

    /**
     * Sets the shape of this inventory.
     *
     * @param shape The {@link InventoryShape} to be used.
     * @return {@link VanillaInventory}
     * @throws IllegalStateException If this inventory's type is not a chest inventory.
     */
    public VanillaInventory setShape(InventoryShape shape) throws IllegalStateException {
        if (type != InventoryType.CHEST) {
            throw new IllegalStateException("can't shape this inventory type");
        }

        this.shape = shape;
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
        if (rows != getShape().getRows()) {
            throw new IllegalArgumentException("invalid number of rows for this shape");
        }
        for (Map.Entry<Integer, ItemStack> slot : shape.getItems().entrySet()) {
            items.add(slot.getKey(), slot.getValue());
        }
        super.create(rows);
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
        for (Map.Entry<Integer, ItemStack> slot : shape.getItems().entrySet()) {
            items.add(slot.getKey(), slot.getValue());
        }
        super.create();
        return this;
    }
}
