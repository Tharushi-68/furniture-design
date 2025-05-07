package com.furnituredesign.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DashboardScreen {

    public static Scene getDashboardScene(Stage primaryStage) {
        // Header
        Label header = new Label("Furniture Designer Dashboard");
        header.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");
        VBox headerBox = new VBox(header);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(20, 0, 30, 0));

        // Buttons
        Button newDesignBtn = new Button("Create New Design");
        Button viewCatalogBtn = new Button("View Furniture Catalog");
        Button logoutBtn = new Button("Logout");

        for (Button btn : new Button[]{newDesignBtn, viewCatalogBtn, logoutBtn}) {
            btn.setPrefWidth(250);
            btn.setStyle(
                    "-fx-font-size: 14px;" +
                            "-fx-background-color: #4CAF50;" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 8;" +
                            "-fx-padding: 10 20;"
            );
            btn.setOnMouseEntered(e -> btn.setStyle(
                    "-fx-font-size: 14px;" +
                            "-fx-background-color: #45a049;" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 8;" +
                            "-fx-padding: 10 20;"
            ));
            btn.setOnMouseExited(e -> btn.setStyle(
                    "-fx-font-size: 14px;" +
                            "-fx-background-color: #4CAF50;" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 8;" +
                            "-fx-padding: 10 20;"
            ));
        }

        // Button actions
        newDesignBtn.setOnAction(e -> primaryStage.setScene(DesignerEditor2DScreen.getEditorScene(primaryStage)));
        viewCatalogBtn.setOnAction(e -> primaryStage.setScene(FurnitureCatalogScreen.getCatalogScene(primaryStage)));
        logoutBtn.setOnAction(e -> primaryStage.setScene(LoginScreen.getLoginScene(primaryStage)));

        VBox buttonBox = new VBox(20, newDesignBtn, viewCatalogBtn, logoutBtn);
        buttonBox.setAlignment(Pos.CENTER);

        // Main layout
        VBox layout = new VBox(20, headerBox, buttonBox);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #f9f9f9, #e0e0e0);");

        return new Scene(layout, 700, 500);
    }
}
