package com.furnituredesign.ui;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * Provides the 3D furniture editor view with interactive scaling controls.
 */
public class DesignerEditor3DView {

    /**
     * Returns a BorderPane layout with the 3D view and size control slider.
     */
    public static BorderPane get3DView() {
        // Get the Canvas3D instance and its 3D scene container
        Canvas3D canvas3D = Canvas3D.getInstance();
        StackPane scenePane = canvas3D.get3DView();

        // Vertical slider to scale selected furniture
        Slider sizeSlider = new Slider(0.1, 3.0, 1.0);
        sizeSlider.setOrientation(Orientation.VERTICAL);
        sizeSlider.setShowTickLabels(true);
        sizeSlider.setShowTickMarks(true);
        sizeSlider.setMajorTickUnit(0.5);
        sizeSlider.setBlockIncrement(0.1);

        // Label for the slider
        Label sliderLabel = new Label("Furniture Size");
        sliderLabel.setPadding(new Insets(5));

        // Update selected furniture scale when slider changes
        sizeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            // Call the setSelectedScale method on the canvas3D instance
            canvas3D.setSelectedScale(newVal.doubleValue());
        });

        // Layout configuration
        BorderPane rightBar = new BorderPane();
        rightBar.setTop(sliderLabel);
        rightBar.setCenter(sizeSlider);
        rightBar.setPadding(new Insets(10));

        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(scenePane);
        mainPane.setRight(rightBar);

        return mainPane;
    }
}