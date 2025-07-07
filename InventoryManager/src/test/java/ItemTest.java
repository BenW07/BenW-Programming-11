package com.example.inventorymanager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemTest {


    //Verifies all item properties are correctly initialized
    @Test
    void testItemCreation() {
        //Creates test item with valid parameters
        Item item = new Item("Laptop", 10, 999.99, "Electronics");

        //Verifies all properties were set correctly
        assertEquals("Laptop", item.getName());
        assertEquals(10, item.getQuantity());
        assertEquals(999.99, item.getPrice(), 0.001);
        assertEquals("Electronics", item.getCategory());
    }

    //Verifies constructor throws exception for negative quantity
    @Test
    void testNegativeQuantityThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            //Attempts to create item with invalid quantity
            new Item("Laptop", -1, 999.99, "Electronics");
        });
    }

    //Verifies quantity * price calculation is correct
    @Test
    void testCalculateTotalValue() {
        //Creates test item
        Item item = new Item("Laptop", 5, 999.99, "Electronics");

        //Verify total value calculation (5 * 999.99)
        assertEquals(4999.95, item.calculateTotalValue(), 0.001);
    }

    //Verifies in-stock/out-of-stock detection
    @Test
    void testIsInStock() {
        //Create in-stock and out-of-stock items
        Item inStock = new Item("Laptop", 1, 999.99, "Electronics");
        Item outOfStock = new Item("Mouse", 0, 19.99, "Electronics");

        //Verify stock status detection
        assertTrue(inStock.isInStock());
        assertFalse(outOfStock.isInStock());
    }
}