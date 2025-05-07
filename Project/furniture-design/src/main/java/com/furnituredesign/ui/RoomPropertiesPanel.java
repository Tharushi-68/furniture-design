package com.furnituredesign.ui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class RoomPropertiesPanel {
    public static VBox getRoomPropertiesPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));
        panel.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc;");

        // Title
        Label title = new Label("Room Properties");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Width Slider
        Label widthLabel = new Label("Width:");
        Slider widthSlider = new Slider(100, 800, 300);
        widthSlider.setShowTickLabels(true);
        widthSlider.setShowTickMarks(true);

        // Height Slider
        Label heightLabel = new Label("Height:");
        Slider heightSlider = new Slider(100, 800, 400);
        heightSlider.setShowTickLabels(true);
        heightSlider.setShowTickMarks(true);

        // Floor Color Picker
        Label floorColorLabel = new Label("Floor Color:");
        ColorPicker floorColorPicker = new ColorPicker(Color.LIGHTGRAY);

        // Wall Color Picker
        Label wallColorLabel = new Label("Wall Color:");
        ColorPicker wallColorPicker = new ColorPicker(Color.BEIGE);

        // Shape Selector
        Label shapeLabel = new Label("Shape:");
        ComboBox<String> shapeBox = new ComboBox<>();
        shapeBox.getItems().addAll("Rectangle", "Oval");
        shapeBox.setValue("Rectangle");

        // Apply Button
        Button applyButton = new Button("Apply");
        applyButton.setOnAction(e -> {
            double width = widthSlider.getValue();
            double height = heightSlider.getValue();
            Color floorColor = floorColorPicker.getValue();
            Color wallColor = wallColorPicker.getValue();
            String shape = shapeBox.getValue().toLowerCase();
            Canvas2D.updateRoomProperties(width, height, floorColor, wallColor, shape);
        });

        panel.getChildren().addAll(
                title,
                widthLabel, widthSlider,
                heightLabel, heightSlider,
                floorColorLabel, floorColorPicker,
                wallColorLabel, wallColorPicker,
                shapeLabel, shapeBox,
                applyButton
        );

        return panel;
    }
}
