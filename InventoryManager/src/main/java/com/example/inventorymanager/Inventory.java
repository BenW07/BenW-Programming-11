package com.example.inventorymanager;

import java.io.*;
import java.util.*;

//Inventory class that handles all the items and categories
public class Inventory {
    private List<Item> items;
    private List<Category> categories;

    //File paths for the inventory and categories txt files
    private static final String INVENTORY_FILE = "data/inventory.txt";
    private static final String CATEGORIES_FILE = "data/categories.txt";

    //Creates new empty inventory and loads data from files if they exist
    public Inventory() {
        this.items = new ArrayList<>();
        this.categories = new ArrayList<>();

        //Creates a data directory if it doesn't exist
        new File("data").mkdirs();

        //Loads data from existing files
        loadInventoryFromFile();
        loadCategoriesFromFile();
    }

    //Requires: Item that's not null and must have a unique name
    //Modifies: this.items and matching category
    //Effects: Adds item to inventory and matching category
    /* Checks if item with same name already exists returns true if item was added successfully and false if item
    already exists */
    public boolean addItem(Item item) {
        //Throws IllegalArgumentException if item is null
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        for (Item existingItem : items) {
            if (existingItem.getName().equalsIgnoreCase(item.getName())) {
                return false;
            }
        }

        items.add(item);

        Category category = findCategory(item.getCategory());
        if (category != null) {
            try {
                category.addItem(item);
            } catch (IllegalArgumentException e) {

            }
        }

        return true;
    }

    //Requires: Item with name
    //Modifies: this.items and matching category
    //Effects Removes matching item name if found (case insensitive)
    public boolean removeItem(String itemName) {
        //Returns true if item removed and false if otherwise
        if (itemName == null || itemName.trim().isEmpty()) {
            return false;
        }

        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getName().equalsIgnoreCase(itemName.trim())) {
                iterator.remove();

                // Remove from category as well
                Category category = findCategory(item.getCategory());
                if (category != null) {
                    category.removeItem(itemName);
                }

                return true;
            }
        }
        return false;
    }

    //Searches for items by name (case insensitive) then returns list of matching items
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

    //Searches for items and then returns a list with matching items (case insensitive)
    public List<Item> searchItems(String searchTerm) {
        List<Item> results = new ArrayList<>();

        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return results;
        }

        String lowerSearchTerm = searchTerm.trim().toLowerCase();

        for (Item item : items) {
            if (item.getName().toLowerCase().contains(lowerSearchTerm)) {
                results.add(item);
            }
        }

        return results;
    }

    //Finds items in a specific category then returns a list of items (case insensitive)
    public List<Item> getItemsByCategory(String categoryName) {
        List<Item> results = new ArrayList<>();

        if (categoryName == null || categoryName.trim().isEmpty()) {
            return results;
        }

        for (Item item : items) {
            if (item.getCategory().equalsIgnoreCase(categoryName.trim())) {
                results.add(item);
            }
        }

        return results;
    }

    //Returns a copy of all items as a measure to prevent external modification
    public List<Item> getAllItems() {
        return new ArrayList<>(items);
    }

    //Requires: Category cannot be null and must have a unique name
    //Modifies: this.categories
    //Effects: Adds new category if unique name
    public boolean addCategory(Category category) {
        //Throws IllegalArgumentException if category is null
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        //Checks for duplicate category names, returns false if category exists and true if category added
        for (Category existingCategory : categories) {
            if (existingCategory.getName().equalsIgnoreCase(category.getName())) {
                return false;
            }
        }

        categories.add(category);
        return true;
    }

    //Removes category with matching name (case insensitive), returns true if removed false otherwise
    public boolean removeCategory(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            return false;
        }

        return categories.removeIf(category -> category.getName().equalsIgnoreCase(categoryName.trim()));
    }

    //Searches category with matching name, returns found category or null if not found (case insensitive)
    public Category findCategory(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            return null;
        }

        for (Category category : categories) {
            if (category.getName().equalsIgnoreCase(categoryName.trim())) {
                return category;
            }
        }
        return null;
    }

    //Returns copy of all categories to prevent external modification
    public List<Category> getAllCategories() {
        return new ArrayList<>(categories);
    }

    //Sums total value (price * quantity) of all items then returns the total inventory value
    public double calculateTotalInventoryValue() {
        double total = 0.0;
        for (Item item : items) {
            total += item.calculateTotalValue();
        }
        return total;
    }

    //Sums quantity of all items then returns the total quantity
    public int calculateTotalInventoryQuantity() {
        int total = 0;
        for (Item item : items) {
            total += item.getQuantity();
        }
        return total;
    }

    //Finds items with a quantity of zero then returns a list with those items
    public List<Item> getOutOfStockItems() {
        List<Item> outOfStock = new ArrayList<>();
        for (Item item : items) {
            if (!item.isInStock()) {
                outOfStock.add(item);
            }
        }
        return outOfStock;
    }

    //Requires threshold >= 0
    //Modifies: Nothing
    //Effects: fFinds all items with quantity <= threshold (but > 0)
    //Returns a list of low-stock items
    public List<Item> getLowStockItems(int threshold) {
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

    //Counts all items then returns the total item count
    public int getItemCount() {
        return items.size();
    }

    //Counts all categories then returns the count
    public int getCategoryCount() {
        return categories.size();
    }

    //Checks if inventory is empty, returns true if empty, false otherwise
    public boolean isEmpty() {
        return items.isEmpty();
    }

    //Requires: Inventory stock
    //Modifies: this.items and this.categories
    //Effects: Removes all items and categories
    public void clearInventory() {
        items.clear();
        categories.clear();
    }

    //Saves the items to INVENTORY_FILE then returns true if successful, false otherwise
    public boolean saveInventoryToFile() {

        new File("data").mkdirs();

        try (PrintWriter writer = new PrintWriter(new FileWriter(INVENTORY_FILE))) {
            for (Item item : items) {
                writer.println(item.toFileString());
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error saving inventory: " + e.getMessage());
            return false;
        }
    }

    //Loads the INVENTORY_FILE if exists then returns true if successful and false otherwise
    public boolean loadInventoryFromFile() {
        File file = new File(INVENTORY_FILE);
        if (!file.exists()) {
            return false;
        }

        items.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Item item = Item.fromFileString(line);
                    items.add(item);
                } catch (IllegalArgumentException e) {
                    System.err.println("Error parsing line: " + line + " - " + e.getMessage());
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error loading inventory: " + e.getMessage());
            return false;
        }
    }

    //Writes all categories to CATEGORIES_FILE then returns true if successful and false otherwise
    public boolean saveCategoriesToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CATEGORIES_FILE))) {
            for (Category category : categories) {
                writer.println(category.getName() + "," + category.getDescription());
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error saving categories: " + e.getMessage());
            return false;
        }
    }

    //Requires: CATEGORIES_FILE
    //Modifies: this.categories
    //Effects: Reads categories from CATEGORIES_FILE if it exists
    //Returns true if successful, false otherwise
    public boolean loadCategoriesFromFile() {
        File file = new File(CATEGORIES_FILE);
        if (!file.exists()) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(",", 2);
                    if (parts.length >= 1) {
                        String name = parts[0];
                        String description = parts.length > 1 ? parts[1] : "";
                        Category category = new Category(name, description);
                        categories.add(category);
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println("Error parsing category line: " + line + " - " + e.getMessage());
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error loading categories: " + e.getMessage());
            return false;
        }
    }

    //Saves both inventory and categories to their respective files then returns true if both saved successfully, false otherwise
    public boolean saveAllData() {
        return saveInventoryToFile() && saveCategoriesToFile();
    }
}