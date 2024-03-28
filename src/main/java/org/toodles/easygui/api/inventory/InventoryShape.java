package org.toodles.easygui.api.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A util for setting item slots and creating unique shapes fast for {@link CustomInventory}.
 */
public class InventoryShape {

    private final LinkedHashMap<Integer, String> shapes = new LinkedHashMap<>();
    private final Map<Character, ItemStack> shapeMap = new HashMap<>();

    private final Pattern pattern = Pattern.compile("[A-Za-z]");
    private final int rows;

    /**
     * Creates an inventory shape which can be used on {@link CustomInventory}.
     *
     * @param rows The amount of rows of the inventory to be shaped.
     * @throws IllegalArgumentException If the amount of rows is greater than 6, or less than 1.
     */
    public InventoryShape(int rows) throws IllegalArgumentException {
        if (rows > 6 || rows < 1) {
            throw new IllegalArgumentException("invalid number of rows");
        }

        this.rows = rows;
    }

    /**
     * Sets the shape of the provided row
     * <p>
     *
     * The format of the row is to represent items with letters ({@code Case Sensitive}). Each type of letter is separated by a '-'.
     * The maximum amount of letters is 9, and the minimum is 1.
     * <p>
     *
     * Ex: "XXXX-yyy-zz" where X, y, and z can represent different items.
     * <p>
     *
     * <b>The letters of the shape in the provided row is synchronous with the shapes of all rows.</b>
     *
     * @param shape The format of the shape of this row. Look above for more information.
     * @param row The row to be shaped.
     */
    public void setShape(String shape, int row) {
        Matcher matcher = pattern.matcher(shape);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("invalid shape format");
        }
        String measurable = shape.replaceAll("-", "");
        if (measurable.length() > 9 || measurable.length() < 1) {
            throw new IllegalArgumentException("invalid shape format");
        }

        if (row <= rows && rows > 0) {
            shapes.put(row, shape);
        }
    }

    /**
     * Returns the shape of all the rows
     *
     * @return Returns the full shape of all the rows
     */
    public String getShape() {
        StringBuilder builder = new StringBuilder();
        for (String shape : shapes.values()) {
            builder.append(shape).append("-");
        }
        return builder.toString();
    }

    /**
     * Maps a letter to an {@link ItemStack} in the full shape. See {@link #setShape(String, int)}
     * for more information.
     *
     * @param letter The letter to be used in the mapping.
     * @param item The {@link ItemStack} that will correspond with the letter.
     */
    public void map(char letter, ItemStack item) {
        Matcher matcher = pattern.matcher(getShape());
        matcher.reset();
        while (matcher.find()) {
            char foundLetter = matcher.group().charAt(0);
            if (foundLetter == letter) {
                shapeMap.put(letter, item);
                return;
            }
        }
        throw new IllegalArgumentException("Letter not found in shape: " + letter);
    }

    /**
     * Returns the map of all the characters and items.
     *
     * @return Returns the shape map.
     */
    public Map<Character, ItemStack> getShapeMap() {
        return shapeMap;
    }

    /**
     * Finds the number of occurrences of a letter in the full shape.
     *
     * @param letter The letter to be found.
     * @return The number of occurrences of the letter in the shape.
     */
    public int findAmount(char letter) {
        String shape = getShape();
        int a = 0;
        for (int i = 0; i < shape.length(); i++) {
            if (shape.charAt(i) == letter) {
                a++;
            }
        }
        return a;
    }

}
