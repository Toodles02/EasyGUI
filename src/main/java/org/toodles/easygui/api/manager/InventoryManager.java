package org.toodles.easygui.api.manager;

import org.bukkit.NamespacedKey;
import org.toodles.easygui.api.inventory.CustomInventory;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The manager of all registered {@link CustomInventory}.
 */
public class InventoryManager {

    private static final Map<NamespacedKey, CustomInventory> inventories = new HashMap<>();

    /**
     * Returns the map of all inventories registered under their namespace.
     *
     * @return A list of registered {@link CustomInventory}.
     */
    public static List<CustomInventory> getInventories() {
        return inventories.values().stream().toList();
    }

    /**
     * Gets a list of {@link CustomInventory} that pass the filter.
     *
     * @param filter The filter for retrieving specific inventories
     * @return A list of {@link CustomInventory} that pass the filter. If there are none, returns an empty {@link ArrayList}
     */
    public static List<CustomInventory> get(Predicate<CustomInventory> filter) {
        if (inventories.values().stream().anyMatch(filter)) {
            return inventories.values().stream().filter(filter).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }


    /**
     * Returns the {@link CustomInventory} registered under the provided {@link NamespacedKey}.
     *
     * @param key The {@link NamespacedKey} used to register that inventory.
     * @return The {@link CustomInventory} registered under the provided {@link NamespacedKey}.
     */
    public static CustomInventory get(NamespacedKey key) {
        return inventories.getOrDefault(key, null);
    }

    /**
     * Registers a {@link CustomInventory} by its {@link NamespacedKey}.
     *
     * @param inventory The {@link CustomInventory} to be registered.
     */
    public static void register(CustomInventory inventory) {
        inventories.put(inventory.getKey(), inventory);
    }

    /**
     * Unregisters an inventory by its {@link NamespacedKey}.
     *
     * @param key The {@link NamespacedKey} of the inventory to be removed.
     */
    public static void unregister(NamespacedKey key) {
        inventories.remove(key);
    }

}
