package com.furnituredesign.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class ToolBarPanel {

    public static ToolBar getToolBar(Stage stage) {
        Button backBtn = new Button("⟵ Back");
        Button saveBtn = new Button("💾 Save");
        Button deleteBtn = new Button("🗑 Delete");

        Button[] buttons = { backBtn, saveBtn, deleteBtn };

        for (Button btn : buttons) {
            btn.setStyle(
                    "-fx-background-color: #3498db;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-size: 13px;" +
                            "-fx-padding: 6 15;" +
                            "-fx-background-radius: 6;"
            );
            btn.setOnMouseEntered(e -> btn.setStyle(
                    "-fx-background-color: #2980b9;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-size: 13px;" +
                            "-fx-padding: 6 15;" +
                            "-fx-background-radius: 6;"
            ));
            btn.setOnMouseExited(e -> btn.setStyle(
                    "-fx-background-color: #3498db;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-size: 13px;" +
                            "-fx-padding: 6 15;" +
                            "-fx-background-radius: 6;"
            ));
        }

        // Action: Save design
        saveBtn.setOnAction(e -> {
            String filePath = "canvas_design.json";
            Canvas2D.saveDesignToFile(filePath);
        });

        // Action: Reset design
        deleteBtn.setOnAction(e -> {
            Canvas2D.resetDesign();
        });

        // Action: Go back to dashboard
        backBtn.setOnAction(e -> {
            stage.setScene(DashboardScreen.getDashboardScene(stage));
        });

        ToolBar toolbar = new ToolBar(backBtn, saveBtn, deleteBtn);
        toolbar.setPadding(new Insets(10));
        toolbar.setStyle("-fx-background-color: #ecf0f1;");

        return toolbar;
    }
}
