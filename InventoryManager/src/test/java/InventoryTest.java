package com.example.inventorymanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private Inventory inventory;

    //Prepares a fresh empty inventory before each test
    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    //Requires: Valid item object
    //Modifies: Inventory state
    //Effects: Verifies item, is added and the count increases
    @Test
    void testAddItem() {
        //Creates test item
        Item item = new Item("Laptop", 5, 999.99, "Electronics");
        //Adds item and verifies success
        assertTrue(inventory.addItem(item));
        //Verifies inventory count is updated
        assertEquals(1, inventory.getItemCount());
    }

    //Requires: inventory with existing items
    //Modifies: inventory state
    //Effects: verifies item removal
    @Test
    void testRemoveItem() {
        //Creates test item and adds it
        Item item = new Item("Laptop", 5, 999.99, "Electronics");
        inventory.addItem(item);
        //Removes item and verifies its success
        assertTrue(inventory.removeItem("Laptop"));
        //Verify inventory count is zero
        assertEquals(0, inventory.getItemCount());
    }

    //Requires: Inventory with multiple items
    //Modifies: Nothing
    //Effects: Verifies search returns correct items
    @Test
    void testSearchItems() {
        //Adds items to populate inventory
        inventory.addItem(new Item("Laptop", 5, 999.99, "Electronics"));
        inventory.addItem(new Item("Mouse", 10, 19.99, "Electronics"));
        inventory.addItem(new Item("Desk", 3, 199.99, "Furniture"));

        //Verify search for "e" finds 2 items in Electronics category
        assertEquals(2, inventory.searchItems("e").size());
        //Verify cas-insensitive search for desk
        assertEquals(1, inventory.searchItems("desk").size());
    }
}