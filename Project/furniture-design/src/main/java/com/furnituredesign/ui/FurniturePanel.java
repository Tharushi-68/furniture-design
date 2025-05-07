package com.furnituredesign.ui;

import com.furnituredesign.services.FurnitureService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class FurniturePanel {

    public static VBox getFurniturePanel() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-border-color: gray; -fx-border-width: 1;");

        Label label = new Label("Available Furniture");

        ListView<String> furnitureList = new ListView<>();
        FurnitureService service = new FurnitureService();

        service.getAvailableFurniture().forEach(f -> furnitureList.getItems().add(f.getType()));

        Button addBtn = new Button("Add Selected");

        addBtn.setOnAction(e -> {
            String selected = furnitureList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Canvas2D.addFurnitureToCanvas(selected);
            }
        });

        box.getChildren().addAll(label, furnitureList, addBtn);

        return box;
    }
}
