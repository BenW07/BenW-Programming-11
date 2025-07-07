package com.example.inventorymanager;

import java.util.ArrayList;
import java.util.List;

//Class storing the category name, description, and items
public class Category {
    private String name;
    private String description;
    private List<Item> items;

    //Constructor creating a new empty category with empty name, description, and item list
    public Category() {
        this.name = "";
        this.description = "";
        this.items = new ArrayList<>();
    }

    //Constructor
    //Requires: Name that's not null or empty
    //Modifies: This
    //Effects: Creates a new category with a given name, description, and empty item list
    public Category(String name, String description) {
        //Validates input parameters and throws IllegalArgumentException if the name is empty or null
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }

        this.name = name.trim();
        this.description = (description != null) ? description.trim() : "";
        this.items = new ArrayList<>();
    }

    //Gets the current name of the category and returns it
    public String getName() {
        return name;
    }

    //Requires: Name not empty or null
    //Modifies: this.name
    //Effects: Updates the category name
    public void setName(String name) {
        //Throws IllegalArgumentException if name is null or empty
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        this.name = name.trim();
    }

    //Gets and returns the current description of the category
    public String getDescription() {
        return description;
    }

    //Updates the description of the category and sets it to empty of null
    //Modifies: this.description
    public void setDescription(String description) {
        this.description = (description != null) ? description.trim() : "";
    }

    //Returns a new list containing the items in the category
    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    //Requires: Item not null or empty, must have unique name within the category
    //Modifies: this.item
    //Effects: Adds the item to the category's collection
    public void addItem(Item item) {
        //Throws IllegalArguementException if item name is null
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        //Checks if an item with a duplicate name is present in the list, while ignoring case sensitivity
        for (Item existingItem : items) {
            if (existingItem.getName().equalsIgnoreCase(item.getName())) {
                throw new IllegalArgumentException("Item with name '" + item.getName() + "' already exists in this category");
            }
        }

        items.add(item);
    }

    //Removes item with matching name if one is found, then returns true if item was removed and false otherwise
    public boolean removeItem(String itemName) {
        if (itemName == null || itemName.trim().isEmpty()) {
            return false;
        }

        return items.removeIf(item -> item.getName().equalsIgnoreCase(itemName.trim()));
    }

    //Searches for items with identical names in this category then returns the found item name or null if not found
    public Item findItem(String itemName) {
        if (itemName == null || itemName.trim().isEmpty()) {
            return null;
        }

        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName.trim())) {
                return item;
            }
        }
        return null;
    }

    //Counts the items in the category (int) then returns the total item count
    public int getItemCount() {
        return items.size();
    }

    //Calculates the total value of all the items in category, then returns the sum of all item values (price * amount)
    public double calculateTotalValue() {
        double total = 0.0;
        for (Item item : items) {
            total += item.calculateTotalValue();
        }
        return total;
    }

    //Calculates the total amount(quantity) of items then returns the total amount(quantity)
    public int calculateTotalQuantity() {
        int total = 0;
        for (Item item : items) {
            total += item.getQuantity();
        }
        return total;
    }

    //Finds out of stock items (quantity = 0) then lists the items meeting this requirement
    public List<Item> getOutOfStockItems() {
        List<Item> outOfStock = new ArrayList<>();
        for (Item item : items) {
            if (!item.isInStock()) {
                outOfStock.add(item);
            }
        }
        return outOfStock;
    }

    //Requires: threshold >= 0
    //Modifies: Nothing
    //Effects: Finds items with quantity <= threshold (> 0) then returns list of items meeting this requirement
    public List<Item> getLowStockItems(int threshold) {
        //If threshold < 0 throws IllegalArgumentException
        if (threshold < 0) {
            throw new IllegalArgumentException("Threshold cannot be negative");
        }

        List<Item> lowStock = new ArrayList<>();
        for (Item item : items) {
            if (item.getQuantity() <= threshold && item.isInStock()) {
                lowStock.add(item);
            }
        }
        return lowStock;
    }

    //Checks if the category has no items, if it does, returns true, otherwise returns false
    public boolean isEmpty() {
        return items.isEmpty();
    }

    //Modifies: this.items
    //Effects: Removes all items from this category
    public void clearItems() {
        items.clear();
    }

    //Creates a formatted string showing category details
    @Override
    public String toString() {
        return String.format("Category: %s | Items: %d | Total Value: $%.2f",
                name, getItemCount(), calculateTotalValue());
    }

    //Compares categories by name (case sensitive)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Category category = (Category) obj;
        return name.equals(category.name);
    }

    //Generates hash code based on category name (unique fingerprint for the category)
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}