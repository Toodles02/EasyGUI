package com.github.toodles02.easygui.api.inventory;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.*;

import java.util.function.Consumer;

/**
 * A handler for handling inventory events, found in {@link VanillaInventory}.
 */
public class InventoryHandler {

    private Consumer<InventoryOpenEvent> openEventHandler = event -> {};
    private Consumer<InventoryCloseEvent> closeEventHandler = event -> {};
    private Consumer<InventoryClickEvent> clickEventHandler = event -> {};

    private Consumer<InventoryDragEvent> dragEventHandler = event -> {};
    private Consumer<InventoryMoveItemEvent> moveItemEventHandler = event -> {};
    private Consumer<InventoryCreativeEvent> creativeEventHandler = event -> {};
    private Consumer<InventoryPickupItemEvent> pickupItemEventHandler = event -> {};


    /**
     * Sets the handler of the provided event type.
     * <p>
     * Supported inventory events:
     * <ul>
     * <li>{@link org.bukkit.event.inventory.InventoryOpenEvent}</li>
     * <li>{@link org.bukkit.event.inventory.InventoryCloseEvent}</li>
     * <li>{@link org.bukkit.event.inventory.InventoryClickEvent}</li>
     * <li>{@link org.bukkit.event.inventory.InventoryDragEvent}</li>
     * <li>{@link org.bukkit.event.inventory.InventoryMoveItemEvent}</li>
     * <li>{@link org.bukkit.event.inventory.InventoryCreativeEvent}</li>
     * <li>{@link org.bukkit.event.inventory.InventoryPickupItemEvent}</li>
     * </ul>
     *
     * @param eClass The type of class for the event.
     * @param handler The handler that will handle the event.
     * @param <T> The type of inventory event which extends {@link Event}.
     * @throws IllegalArgumentException If the event type is not a supported inventory event.
     */
    @SuppressWarnings("unchecked")
    public <T extends Event> void setHandler(Class<T> eClass, Consumer<T> handler) throws IllegalArgumentException {
        if (eClass.isAssignableFrom(InventoryOpenEvent.class)) {
            openEventHandler = (Consumer<InventoryOpenEvent>) handler;
        } else if (eClass.isAssignableFrom(InventoryCloseEvent.class)) {
            closeEventHandler = (Consumer<InventoryCloseEvent>) handler;
        } else if (eClass.isAssignableFrom(InventoryClickEvent.class)) {
            clickEventHandler = (Consumer<InventoryClickEvent>) handler;
        } else if (eClass.isAssignableFrom(InventoryDragEvent.class)) {
            dragEventHandler = (Consumer<InventoryDragEvent>) handler;
        } else if (eClass.isAssignableFrom(InventoryMoveItemEvent.class)) {
            moveItemEventHandler = (Consumer<InventoryMoveItemEvent>) handler;
        } else if (eClass.isAssignableFrom(InventoryCreativeEvent.class)) {
            creativeEventHandler = (Consumer<InventoryCreativeEvent>) handler;
        } else if (eClass.isAssignableFrom(InventoryPickupItemEvent.class)) {
            pickupItemEventHandler = (Consumer<InventoryPickupItemEvent>) handler;
        } else {
            throw new IllegalArgumentException("invalid event type");
        }
    }


    /**
     * Handles the provided event if the event is a supported inventory event.
     * <p>
     * Supported inventory events:
     * <ul>
     * <li>{@link org.bukkit.event.inventory.InventoryOpenEvent}</li>
     * <li>{@link org.bukkit.event.inventory.InventoryCloseEvent}</li>
     * <li>{@link org.bukkit.event.inventory.InventoryClickEvent}</li>
     * <li>{@link org.bukkit.event.inventory.InventoryDragEvent}</li>
     * <li>{@link org.bukkit.event.inventory.InventoryMoveItemEvent}</li>
     * <li>{@link org.bukkit.event.inventory.InventoryCreativeEvent}</li>
     * <li>{@link org.bukkit.event.inventory.InventoryPickupItemEvent}</li>
     * </ul>
     *
     * @param event The instance of the event to be handled.
     * @param <T> The type of event to handle.
     * @throws IllegalArgumentException If the provided event type is not supported.
     */
    public <T extends Event> void handle(T event) throws IllegalArgumentException {

        Class<? extends Event> eClass = event.getClass();

        if (eClass.isAssignableFrom(InventoryOpenEvent.class)) {
            openEventHandler.accept((InventoryOpenEvent) event);
        } else if (eClass.isAssignableFrom(InventoryCloseEvent.class)) {
            closeEventHandler.accept((InventoryCloseEvent) event);
        } else if (eClass.isAssignableFrom(InventoryClickEvent.class)) {
            clickEventHandler.accept((InventoryClickEvent) event);
        } else if (eClass.isAssignableFrom(InventoryDragEvent.class)) {
            dragEventHandler.accept((InventoryDragEvent) event);
        } else if (eClass.isAssignableFrom(InventoryMoveItemEvent.class)) {
            moveItemEventHandler.accept((InventoryMoveItemEvent) event);
        } else if (eClass.isAssignableFrom(InventoryCreativeEvent.class)) {
            creativeEventHandler.accept((InventoryCreativeEvent) event);
        } else if (eClass.isAssignableFrom(InventoryPickupItemEvent.class)) {
            pickupItemEventHandler.accept((InventoryPickupItemEvent) event);
        } else {
            throw new IllegalArgumentException("invalid event type");
        }
    }


}
