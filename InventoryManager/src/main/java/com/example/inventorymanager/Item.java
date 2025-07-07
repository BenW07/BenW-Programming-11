package com.example.inventorymanager;

//Represents a single inventory item with a name, quantity, price, and category
public class Item {
    //Fields stored in the item properties
    private String name;
    private int quantity;
    private double price;
    private String category;

    //Creates an empty item with default values
    public Item() {
        this.name = "";
        this.quantity = 0;
        this.price = 0.0;
        this.category = "";
    }

    //Requires: Name, Category not null or empty, quantity/price >= 0
    //Modifies: Item
    //Effects: Creates a new item with specified properties
    public Item(String name, int quantity, double price, String category) {
        //Validates input parameters, throws IllegalArguementException if error validating
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }

        //Assigns the trimmed values
        this.name = name.trim();
        this.quantity = quantity;
        this.price = price;
        this.category = category.trim();
    }

    //Gets and returns the item name
    public String getName() {
        return name;
    }

    //Requires: Name not empty/null
    //Modifies: this.name
    //Effects: Updates the item name
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty");
        }
        this.name = name.trim();
    }

    //Gets and returns the current quantity
    public int getQuantity() {
        return quantity;
    }

    //Requires: quantity >= 0
    //Modifies: this.quantity
    //Effects: Updates the item quantity
    public void setQuantity(int quantity) {
        if (quantity < 0) {
            //Throws IllegalArgumentException if error occurs
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    //Gets the price of an item then returns that price
    public double getPrice() {
        return price;
    }

    //Requires: price >= 0
    //Modifies: this.price
    //Effects: Updates the item price
    public void setPrice(double price) {
        if (price < 0) {
            //Throws IllegalArgumentException if error occurs
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    //Gets and returns the category name
    public String getCategory() {
        return category;
    }

    //Requires: Category not null or empty
    //Modifies: this.category
    //Effects: Updates the item category
    public void setCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            //Throws IllegalArgumentException if category is invalid
            throw new IllegalArgumentException("Category cannot be null or empty");
        }
        this.category = category.trim();
    }

    //Calculates the total value of an item (quantity * price)
    public double calculateTotalValue() {
        return quantity * price;
    }

    //Returns true if quantity > 0, false otherwise
    public boolean isInStock() {
        return quantity > 0;
    }

    //Requires: amount > 0
    //Modifies: this.quantity
    //Effects: Increases quantity by amount <= 0
    public void addStock(int amount) {
        if (amount <= 0) {
            //Throws IllegalArgumentException if amount is not applicable
            throw new IllegalArgumentException("Amount to add must be positive");
        }
        this.quantity += amount;
    }

    //Requires: amount > 0
    //Modifies: this.quantity
    //Effects: Decreases quantity by amount if possible
    public boolean removeStock(int amount) {
        //Returns true if successful, false if not enough stock
        //Throws IllegalArgumentException if amount <= 0
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount to remove must be positive");
        }
        if (amount > quantity) {
            return false;
        }
        this.quantity -= amount;
        return true;
    }

    //Returns CSV string of item properties
    public String toFileString() {
        return name + "," + quantity + "," + price + "," + category;
    }

    //Requires: Properly formatted CSV string
    //Modifies: Nothing
    //Effects: Creates new item from file data
    public static Item fromFileString(String fileString) {
        //Throws IllegalArgumentException if invalid format
        String[] parts = fileString.split(",");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid file format");
        }

        try {
            String name = parts[0];
            int quantity = Integer.parseInt(parts[1]);
            double price = Double.parseDouble(parts[2]);
            String category = parts[3];

            return new Item(name, quantity, price, category);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format in file");
        }
    }

    //Returns formatted string including all properties
    @Override
    public String toString() {
        return String.format("Item: %s | Qty: %d | Price: $%.2f | Category: %s | Total Value: $%.2f",
                name, quantity, price, category, calculateTotalValue());
    }

    //Items are equal if name and category match
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Item item = (Item) obj;
        return name.equals(item.name) && category.equals(item.category);
    }

    //Returns hash based on the name and category
    @Override
    public int hashCode() {
        return name.hashCode() + category.hashCode();
    }
}