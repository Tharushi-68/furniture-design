package com.furnituredesign.ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DesignerEditor2DScreen {
    public static Scene getEditorScene(Stage primaryStage) {
        BorderPane root = new BorderPane();
        // Top Toolbar
        root.setTop(ToolBarPanel.getToolBar(primaryStage));
        // Left Panel: Room + Furniture selection
        VBox leftPanel = new VBox(10);
        leftPanel.setPadding(new Insets(10));
        leftPanel.getChildren().addAll(
                RoomPropertiesPanel.getRoomPropertiesPanel(),
                FurniturePanel.getFurniturePanel()
        );
        leftPanel.setPrefWidth(250);

        // Center Split: 2D & 3D Views
        SplitPane centerSplit = new SplitPane();
        centerSplit.setDividerPositions(0.35);
        centerSplit.getItems().addAll(
                Canvas2D.get2DCanvas(),
                DesignerEditor3DView.get3DView()
        );

        root.setLeft(leftPanel);
        root.setCenter(centerSplit);

        // Create scene without fixed dimensions to adapt to stage size
        return new Scene(root,1200,800);
    }
}