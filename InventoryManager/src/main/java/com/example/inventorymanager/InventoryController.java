package com.example.inventorymanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

//Java imports meant for network and utilities
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    @FXML private TextField itemNameField; //Input field for item name
    @FXML private TextField itemQuantityField; //Input field for item quantity
    @FXML private TextField itemPriceField; //Input field for item price
    @FXML private ComboBox<String> categoryComboBox; //Dropdown for categories
    @FXML private TextField searchField; //Search input field
    @FXML private TextField newCategoryField; //Input for new category name
    @FXML private TextField categoryDescriptionField; //Input for category description
    @FXML private TableView<Item> inventoryTable; //Main inventory table
    @FXML private TableColumn<Item, String> nameColumn; //Column for item names
    @FXML private TableColumn<Item, Integer> quantityColumn; //Column for quantities
    @FXML private TableColumn<Item, Double> priceColumn; //Column for prices
    @FXML private TableColumn<Item, String> categoryColumn; //Column for categories
    @FXML private TableColumn<Item, Double> totalValueColumn; //Column for total value
    @FXML private Label totalItemsLabel; //Label showing total item count
    @FXML private Label totalValueLabel; //Label showing total inventory value
    @FXML private Label statusLabel; //Label for status messages
    @FXML private ListView<String> categoriesListView; //List view for categories
    @FXML private Button addItemButton; //Button to add items
    @FXML private Button removeItemButton; //Button to remove items
    @FXML private Button updateItemButton; //Button to update items
    @FXML private Button searchButton; //Button to search items
    @FXML private Button clearSearchButton; //Button to clear search
    @FXML private Button addCategoryButton; //Button to add categories
    @FXML private Button removeCategoryButton; //Button to remove categories
    @FXML private Button saveButton; //Button to save data
    @FXML private Button loadButton; //Button to load data
    @FXML private Button showLowStockButton; //Button to show low stock items
    @FXML private Button showOutOfStockButton; //Button to show out-of-stock items

    //Business logic
    private Inventory inventory;
    private ObservableList<Item> itemList;
    private ObservableList<String> categoryList;

    //Sets up UI components, initial data, and configures handlers
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Initializes the inventory and observable lists
        inventory = new Inventory();
        itemList = FXCollections.observableArrayList();
        categoryList = FXCollections.observableArrayList();

        //Sets up table columns
        setupTableColumns();

        //Sets up the table
        inventoryTable.setItems(itemList);

        //Sets up the category combo box and list view
        categoryComboBox.setItems(categoryList);
        categoriesListView.setItems(categoryList);

        //Loads initial data
        loadInventoryData();
        loadCategoriesData();

        //Sets up event handlers
        setupEventHandlers();

        //Updates the UI
        updateUI();

        //Sets status
        setStatus("Application loaded successfully");
    }

    //Sets up the cell value formatter for all columns in table
    private void setupTableColumns() {
        //Basic column setups name, quantity, price, category
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        //Custom total value column
        totalValueColumn.setCellValueFactory(cellData -> {
            Item item = cellData.getValue();
            return new javafx.beans.property.SimpleDoubleProperty(item.calculateTotalValue()).asObject();
        });

        //Formats price and total value columns to show currency
        priceColumn.setCellFactory(column -> new TableCell<Item, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", item));
                }
            }
        });

        //Formats total value column
        totalValueColumn.setCellFactory(column -> new TableCell<Item, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", item));
                }
            }
        });
    }

    //Sets up eventHandlers for user interactions
    private void setupEventHandlers() {
        //Table selection handler
        inventoryTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
            }
        });

        //Input validation for quantity field
        itemQuantityField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                itemQuantityField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        //Input validation for price field
        itemPriceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d{0,2})?")) {
                itemPriceField.setText(oldValue);
            }
        });

        //Enter key handlers meant for quick data entry
        itemNameField.setOnAction(e -> addItem());
        itemQuantityField.setOnAction(e -> addItem());
        itemPriceField.setOnAction(e -> addItem());
        searchField.setOnAction(e -> searchItems());
        newCategoryField.setOnAction(e -> addCategory());
    }

    //Fills the input fields with data from the selected item (unless null)
    private void populateFields(Item item) {
        if (item != null) {
            itemNameField.setText(item.getName());
            itemQuantityField.setText(String.valueOf(item.getQuantity()));
            itemPriceField.setText(String.valueOf(item.getPrice()));
            categoryComboBox.setValue(item.getCategory());
        }
    }

    //Requires: Valid input in all fields
    //Modifies: Inventory, itemList
    //Effects: Adds new item to inventory if valid, updates UI
    @FXML
    private void addItem() {
        try {
            //Get values from input fields
            String name = itemNameField.getText().trim();
            String quantityText = itemQuantityField.getText().trim();
            String priceText = itemPriceField.getText().trim();
            String category = categoryComboBox.getValue();

            //Gets and validates input
            if (name.isEmpty()) {
                showError("Item name cannot be empty");
                return;
            }

            if (quantityText.isEmpty()) {
                showError("Quantity cannot be empty");
                return;
            }

            if (priceText.isEmpty()) {
                showError("Price cannot be empty");
                return;
            }

            if (category == null || category.trim().isEmpty()) {
                showError("Please select a category");
                return;
            }

            int quantity;
            double price;

            //Parses and validates numbers, returns error if not valid
            try {
                quantity = Integer.parseInt(quantityText);
                if (quantity < 0) {
                    showError("Quantity cannot be negative");
                    return;
                }
            } catch (NumberFormatException e) {
                showError("Invalid quantity format");
                return;
            }

            try {
                price = Double.parseDouble(priceText);
                if (price < 0) {
                    showError("Price cannot be negative");
                    return;
                }
            } catch (NumberFormatException e) {
                showError("Invalid price format");
                return;
            }

            //Creates and adds the item
            Item newItem = new Item(name, quantity, price, category);

            if (inventory.addItem(newItem)) {
                clearFields();
                loadInventoryData();
                updateUI();
                setStatus("Item added successfully: " + name);
            } else {
                showError("Item with this name already exists");
            }

        } catch (IllegalArgumentException e) {
            showError("Error adding item: " + e.getMessage());
        } catch (Exception e) {
            showError("Unexpected error: " + e.getMessage());
        }
    }

    //Requires: Item selected from table
    //Modifies: inventory, itemlist
    //Effects: Removes selected item after confirmation, updates UI
    @FXML
    private void removeItem() {
        Item selectedItem = inventoryTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showError("Please select an item to remove");
            return;
        }

        //Confirm deletion
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Remove Item");
        alert.setContentText("Are you sure you want to remove: " + selectedItem.getName() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (inventory.removeItem(selectedItem.getName())) {
                clearFields();
                loadInventoryData();
                updateUI();
                setStatus("Item removed successfully: " + selectedItem.getName());
            } else {
                showError("Error removing item");
            }
        }
    }

    //Requires: Item selected in table and valid input
    //Modifies: Selected item
    //Effects: Updates item properties, refreshes table
    @FXML
    private void updateItem() {
        Item selectedItem = inventoryTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showError("Please select an item to update");
            return;
        }

        try {
            //Gets values from input fields
            String quantityText = itemQuantityField.getText().trim();
            String priceText = itemPriceField.getText().trim();
            String category = categoryComboBox.getValue();

            //Validates input
            if (quantityText.isEmpty()) {
                showError("Quantity cannot be empty");
                return;
            }

            if (priceText.isEmpty()) {
                showError("Price cannot be empty");
                return;
            }

            if (category == null || category.trim().isEmpty()) {
                showError("Please select a category");
                return;
            }

            //Parses and validates values
            int quantity;
            double price;

            try {
                quantity = Integer.parseInt(quantityText);
                if (quantity < 0) {
                    showError("Quantity cannot be negative");
                    return;
                }
            } catch (NumberFormatException e) {
                showError("Invalid quantity format");
                return;
            }

            try {
                price = Double.parseDouble(priceText);
                if (price < 0) {
                    showError("Price cannot be negative");
                    return;
                }
            } catch (NumberFormatException e) {
                showError("Invalid price format");
                return;
            }

            //Updates the item
            selectedItem.setQuantity(quantity);
            selectedItem.setPrice(price);
            selectedItem.setCategory(category);

            inventoryTable.refresh();
            updateUI();
            setStatus("Item updated successfully: " + selectedItem.getName());

        } catch (IllegalArgumentException e) {
            showError("Error updating item: " + e.getMessage());
        } catch (Exception e) {
            showError("Unexpected error: " + e.getMessage());
        }
    }

    //Filters table to show items matching search term
    @FXML
    private void searchItems() {
        String searchTerm = searchField.getText().trim();

        if (searchTerm.isEmpty()) {
            loadInventoryData();
            setStatus("Showing all items");
            return;
        }

        List<Item> searchResults = inventory.searchItems(searchTerm);
        itemList.clear();
        itemList.addAll(searchResults);

        setStatus("Found " + searchResults.size() + " items matching '" + searchTerm + "'");
    }

    //Resets table to show all of the items
    @FXML
    private void clearSearch() {
        searchField.clear();
        loadInventoryData();
        setStatus("Showing all items");
    }

    //Requires: Category name not empty
    //Modifies: inventory, categoryList
    //Effects: Adds new category if unique name
    @FXML
    private void addCategory() {
        String name = newCategoryField.getText().trim();
        String description = categoryDescriptionField.getText().trim();

        if (name.isEmpty()) {
            showError("Category name cannot be empty");
            return;
        }

        try {
            Category newCategory = new Category(name, description);

            if (inventory.addCategory(newCategory)) {
                newCategoryField.clear();
                categoryDescriptionField.clear();
                loadCategoriesData();
                setStatus("Category added successfully: " + name);
            } else {
                showError("Category with this name already exists");
            }

        } catch (IllegalArgumentException e) {
            showError("Error adding category: " + e.getMessage());
        }
    }

    //Requires: Category selected and empty
    //Modifies: inventory, categoryList
    //Effects: Removes category after confirmation
    @FXML
    private void removeCategory() {
        String selectedCategory = categoriesListView.getSelectionModel().getSelectedItem();

        if (selectedCategory == null) {
            showError("Please select a category to remove");
            return;
        }

        //Checks if category has items
        List<Item> itemsInCategory = inventory.getItemsByCategory(selectedCategory);
        if (!itemsInCategory.isEmpty()) {
            showError("Cannot remove category that contains items. Remove items first.");
            return;
        }

        //Confirms deletion
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Remove Category");
        alert.setContentText("Are you sure you want to remove category: " + selectedCategory + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (inventory.removeCategory(selectedCategory)) {
                loadCategoriesData();
                setStatus("Category removed successfully: " + selectedCategory);
            } else {
                showError("Error removing category");
            }
        }
    }

    //Prompts the user to input a threshold and shows matching items equal to or lower to that value
    @FXML
    private void showLowStock() {
        TextInputDialog dialog = new TextInputDialog("5");
        dialog.setTitle("Low Stock Threshold");
        dialog.setHeaderText("Enter Low Stock Threshold");
        dialog.setContentText("Items with quantity less than or equal to:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                int threshold = Integer.parseInt(result.get());
                if (threshold < 0) {
                    showError("Threshold cannot be negative");
                    return;
                }

                List<Item> lowStockItems = inventory.getLowStockItems(threshold);
                itemList.clear();
                itemList.addAll(lowStockItems);

                setStatus("Showing " + lowStockItems.size() + " low stock items (threshold: " + threshold + ")");

            } catch (NumberFormatException e) {
                showError("Invalid threshold value");
            }
        }
    }

    //Displays the items with zero quantity
    @FXML
    private void showOutOfStock() {
        List<Item> outOfStockItems = inventory.getOutOfStockItems();
        itemList.clear();
        itemList.addAll(outOfStockItems);

        setStatus("Showing " + outOfStockItems.size() + " out of stock items");
    }

    //Saves user data to the inventory and category files
    @FXML
    private void saveData() {
        if (inventory.saveAllData()) {
            setStatus("Data saved successfully");
        } else {
            showError("Error saving data");
        }
    }

    //Loads inventory and categories from files
    @FXML
    private void loadData() {

        itemList.clear();
        categoryList.clear();

        if (inventory.loadInventoryFromFile() && inventory.loadCategoriesFromFile()) {
            loadInventoryData();
            loadCategoriesData();
            updateUI();
            setStatus("Data loaded successfully from txt files");
        } else {
            showError("Error loading data");
        }
    }

    //Updates itemList with the current inventory
    private void loadInventoryData() {
        itemList.clear();
        itemList.addAll(inventory.getAllItems());
    }

    //Updates categoryList with current categories
    private void loadCategoriesData() {
        categoryList.clear();
        for (Category category : inventory.getAllCategories()) {
            categoryList.add(category.getName());
        }
    }

    //Refreshes total items and value displays
    private void updateUI() {
        totalItemsLabel.setText("Total Items: " + inventory.getItemCount());
        totalValueLabel.setText(String.format("Total Value: $%.2f", inventory.calculateTotalInventoryValue()));
    }

    //Resets all the input fields and selections
    private void clearFields() {
        itemNameField.clear();
        itemQuantityField.clear();
        itemPriceField.clear();
        categoryComboBox.setValue(null);
        inventoryTable.getSelectionModel().clearSelection();
    }

    //Updates status label text
    private void setStatus(String message) {
        statusLabel.setText(message);
    }

    //Displays error alert with given message
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(message);
        alert.showAndWait();

        setStatus("Error: " + message);
    }

    //Displays information alert with the message specified
    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}