package com.example.inventorymanager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    //Tests the basic category creation by checking the name and description
    @Test
    void testCategoryCreation() {
        //Creates category
        Category category = new Category("Electronics", "Electronic devices");
        //Verifies name is correct
        assertEquals("Electronics", category.getName());
        //Verifies description is correct
        assertEquals("Electronic devices", category.getDescription());
    }

    //Requires: Category and item with matching category name
    //Modifies: The category's item list
    //Effects: Verifies the item is added and can be retrieved
    @Test
    void testAddItemToCategory() {
        //Creates new category and item
        Category category = new Category("Electronics", "");
        Item item = new Item("Laptop", 5, 999.99, "Electronics");

        //Adds the item to the category
        category.addItem(item);
        //Verifies category now contains one item
        assertEquals(1, category.getItemCount());
        //Verifies the item is found under the correct name
        assertEquals(item, category.findItem("Laptop"));
    }

    //Requires: Category and two items with same name
    //Modifies: Nothing
    //Effects: Verifies the exception is thrown when adding duplicate item
    @Test
    void testDuplicateItemThrowsException() {
        //Creates a category
        Category category = new Category("Electronics", "");

        //Creates two item with the same name but different details
        Item item1 = new Item("Laptop", 5, 999.99, "Electronics");
        Item item2 = new Item("Laptop", 3, 899.99, "Electronics");

        //Add first item
        category.addItem(item1);
        //Verify second item with same name throws exception
        assertThrows(IllegalArgumentException.class, () -> category.addItem(item2));
    }
}