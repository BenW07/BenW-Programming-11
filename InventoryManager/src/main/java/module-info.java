module com.example.inventorymanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.inventorymanager to javafx.fxml;
    exports com.example.inventorymanager;
}