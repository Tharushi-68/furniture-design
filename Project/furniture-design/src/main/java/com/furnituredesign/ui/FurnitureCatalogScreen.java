package com.furnituredesign.ui;

import com.furnituredesign.models.Furniture;
import com.furnituredesign.services.FurnitureService;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Alert;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class FurnitureCatalogScreen {

    private static double anchorX, anchorY;
    private static double angleX = 0;
    private static double angleY = 0;
    private static Group modelGroup;
    private static MeshView[] currentMeshViews;
    private static boolean showDebugBoundingBox = false;

    public static Scene getCatalogScene(Stage primaryStage) {
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom right, #f0f0f0, #e0e0e0);");

        // Header
        HBox header = createHeader();
        mainLayout.setTop(header);

        // Left panel - Furniture List
        VBox leftPanel = createFurnitureListPanel(primaryStage);

        // Center panel - 3D Model View
        VBox centerPanel = create3DModelPanel();

        // Right panel - Controls
        VBox rightPanel = createControlPanel();

        // Set up the main content
        HBox content = new HBox(20, leftPanel, centerPanel, rightPanel);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        mainLayout.setCenter(content);

        // Footer with back button
        HBox footer = createFooter(primaryStage);
        mainLayout.setBottom(footer);

        return new Scene(mainLayout, 1200, 800);
    }

    private static HBox createHeader() {
        Label title = new Label("Furniture Design Catalog");
        title.setFont(Font.font("Helvetica", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#333333"));

        HBox header = new HBox(title);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20, 10, 20, 10));
        header.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        return header;
    }

    private static VBox createFurnitureListPanel(Stage primaryStage) {
        Label listTitle = new Label("Available Furniture");
        listTitle.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));

        // Furniture list with improved styling
        ListView<HBox> furnitureList = new ListView<>();
        furnitureList.setPrefWidth(250);
        furnitureList.setStyle("-fx-background-color: white; -fx-background-radius: 5px;");

        FurnitureService furnitureService = new FurnitureService();
        List<Furniture> models = furnitureService.getAvailableFurniture();

        for (Furniture model : models) {
            HBox itemRow = new HBox(10);

            Label itemLabel = new Label(model.getType());
            itemLabel.setFont(Font.font("Helvetica", 14));

            Circle colorIndicator = new Circle(8);
            colorIndicator.setFill(Color.web(model.getColor()));
            colorIndicator.setStroke(Color.GRAY);
            colorIndicator.setStrokeWidth(1);

            itemRow.getChildren().addAll(colorIndicator, itemLabel);
            itemRow.setAlignment(Pos.CENTER_LEFT);
            itemRow.setPadding(new Insets(5));

            furnitureList.getItems().add(itemRow);
        }

        // Selection behavior
        furnitureList.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.intValue() >= 0 && newVal.intValue() < models.size()) {
                loadFurnitureModel(models.get(newVal.intValue()));
            }
        });

        TextField searchField = new TextField();
        searchField.setPromptText("Search furniture...");
        searchField.setStyle("-fx-background-radius: 20px; -fx-padding: 8px 15px;");

        VBox leftPanel = new VBox(15, listTitle, searchField, furnitureList);
        leftPanel.setPadding(new Insets(10));
        leftPanel.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        return leftPanel;
    }

    private static VBox create3DModelPanel() {
        Label viewTitle = new Label("3D Preview");
        viewTitle.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));

        // Create 3D model display area
        modelGroup = new Group();
        SubScene subScene = new SubScene(modelGroup, 600, 500, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.web("#f8f8f8"));

        // Create and position camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-500);
        camera.setFieldOfView(30);
        subScene.setCamera(camera);

        // Add lighting
        AmbientLight ambientLight = new AmbientLight(Color.WHITE);
        ambientLight.setLightOn(true);

        PointLight pointLight = new PointLight(Color.WHITE);
        pointLight.setTranslateX(400);
        pointLight.setTranslateY(-400);
        pointLight.setTranslateZ(-400);

        // Add a coordinate system visual aid (temporary for debugging)
        Group axisGroup = createAxisGroup();

        modelGroup.getChildren().addAll(ambientLight, pointLight, axisGroup);

        // Add rotation controls
        Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
        modelGroup.getTransforms().addAll(rotateX, rotateY);

        subScene.setOnMousePressed((MouseEvent e) -> {
            anchorX = e.getSceneX();
            anchorY = e.getSceneY();
        });

        subScene.setOnMouseDragged((MouseEvent e) -> {
            rotateX.setAngle(rotateX.getAngle() - (e.getSceneY() - anchorY) / 2);
            rotateY.setAngle(rotateY.getAngle() + (e.getSceneX() - anchorX) / 2);
            anchorX = e.getSceneX();
            anchorY = e.getSceneY();
        });

        // Add zoom functionality
        subScene.addEventHandler(ScrollEvent.SCROLL, e -> {
            double zoomFactor = 1.05;
            double deltaY = e.getDeltaY();
            if (deltaY < 0) {
                zoomFactor = 1/zoomFactor;
            }
            camera.setTranslateZ(camera.getTranslateZ() * zoomFactor);
        });

        HBox controlButtons = new HBox(10);
        Button resetViewButton = new Button("Reset View");
        resetViewButton.setOnAction(e -> {
            rotateX.setAngle(0);
            rotateY.setAngle(0);
            camera.setTranslateZ(-500);
        });

        Button toggleAxesButton = new Button("Toggle Axes");
        toggleAxesButton.setOnAction(e -> {
            axisGroup.setVisible(!axisGroup.isVisible());
        });

        controlButtons.getChildren().addAll(resetViewButton, toggleAxesButton);
        controlButtons.setAlignment(Pos.CENTER);

        Label instructionLabel = new Label("Click and drag to rotate • Scroll to zoom");
        instructionLabel.setTextFill(Color.GRAY);

        VBox centerPanel = new VBox(15, viewTitle, subScene, controlButtons, instructionLabel);
        centerPanel.setAlignment(Pos.TOP_CENTER);
        centerPanel.setPadding(new Insets(10));
        centerPanel.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        return centerPanel;
    }

    private static Group createAxisGroup() {
        Group axisGroup = new Group();
        axisGroup.setId("axisGroup");

        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        final Box xAxis = new Box(240.0, 1, 1);
        final Box yAxis = new Box(1, 240.0, 1);
        final Box zAxis = new Box(1, 1, 240.0);

        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);

        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        return axisGroup;
    }

    private static VBox createControlPanel() {
        Label controlTitle = new Label("Customize");
        controlTitle.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));

        // Size control
        Label sizeLabel = new Label("Size");
        Slider sizeSlider = new Slider(50, 200, 100);
        sizeSlider.setShowTickLabels(true);
        sizeSlider.setShowTickMarks(true);
        sizeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (currentMeshViews != null) {
                for (MeshView mesh : currentMeshViews) {
                    double scale = newVal.doubleValue() / 100.0;
                    mesh.setScaleX(scale);
                    mesh.setScaleY(scale);
                    mesh.setScaleZ(scale);
                }
            }
        });

        // Shadow control
        Label shadowLabel = new Label("Effects");
        CheckBox shadowToggle = new CheckBox("Apply Shadow");
        shadowToggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (currentMeshViews != null) {
                for (MeshView mesh : currentMeshViews) {
                    if (newVal) {
                        mesh.setEffect(new DropShadow(20, Color.color(0, 0, 0, 0.5)));
                    } else {
                        mesh.setEffect(null);
                    }
                }
            }
        });

        // Color selection
        Label colorLabel = new Label("Color");
        ComboBox<String> colorPicker = new ComboBox<>();
        colorPicker.getItems().addAll("Red", "Blue", "Green", "Yellow", "Black", "White", "Purple", "Beige");
        colorPicker.setValue("Red");
        colorPicker.setOnAction(e -> {
            if (currentMeshViews != null) {
                String colorName = colorPicker.getValue();
                for (MeshView mesh : currentMeshViews) {
                    mesh.setMaterial(new PhongMaterial(Color.web(colorName)));
                }
            }
        });

        VBox controlPane = new VBox(15);
        controlPane.getChildren().addAll(
                controlTitle,
                new Separator(),
                sizeLabel, sizeSlider,
                new Separator(),
                shadowLabel, shadowToggle,
                new Separator(),
                colorLabel, colorPicker
        );

        controlPane.setPadding(new Insets(15));
        controlPane.setPrefWidth(220);
        controlPane.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        return controlPane;
    }

    private static HBox createFooter(Stage primaryStage) {
        Button backBtn = new Button("Back to Dashboard");
        backBtn.setStyle("-fx-background-color: #4a86e8; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5;");
        backBtn.setOnAction(e -> primaryStage.setScene(DashboardScreen.getDashboardScene(primaryStage)));

        HBox footer = new HBox(backBtn);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(20));

        return footer;
    }

    private static void loadFurnitureModel(Furniture furniture) {
        // Clear everything from the model group except lighting and axes
        List<Node> nodesToKeep = new ArrayList<>();

        // First identify what to keep (lights and axes)
        for (Node node : modelGroup.getChildren()) {
            if (node instanceof AmbientLight || node instanceof PointLight ||
                    (node instanceof Group && "axisGroup".equals(node.getId()))) {
                nodesToKeep.add(node);
            }
        }

        // Clear the model group completely
        modelGroup.getChildren().clear();

        // Add back the items we want to keep
        modelGroup.getChildren().addAll(nodesToKeep);

        try {
            // Load OBJ file
            ObjModelImporter importer = new ObjModelImporter();
            File modelFile = new File("src/main/resources/" + furniture.getModelPath());

            if (!modelFile.exists()) {
                showModelError("Model file not found: " + furniture.getModelPath());
                return;
            }

            importer.read(modelFile.toURI().toString());
            currentMeshViews = importer.getImport();

            if (currentMeshViews == null || currentMeshViews.length == 0) {
                showModelError("No 3D content found in model file");
                return;
            }

            // Create a Group to hold the model meshes
            Group modelMeshGroup = new Group();
            modelMeshGroup.setId("currentModelGroup");

            // Calculate the model's bounds to center it
            double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;
            double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
            double minZ = Double.MAX_VALUE, maxZ = Double.MIN_VALUE;

            // First pass - calculate bounds
            for (MeshView mesh : currentMeshViews) {
                // Get the mesh's bounds
                javafx.geometry.Bounds bounds = mesh.getBoundsInLocal();
                minX = Math.min(minX, bounds.getMinX());
                maxX = Math.max(maxX, bounds.getMaxX());
                minY = Math.min(minY, bounds.getMinY());
                maxY = Math.max(maxY, bounds.getMaxY());
                minZ = Math.min(minZ, bounds.getMinZ());
                maxZ = Math.max(maxZ, bounds.getMaxZ());
            }

            // Calculate center point
            double centerX = (minX + maxX) / 2;
            double centerY = (minY + maxY) / 2;
            double centerZ = (minZ + maxZ) / 2;

            // Calculate the model's size for scaling
            double width = maxX - minX;
            double height = maxY - minY;
            double depth = maxZ - minZ;
            double maxDimension = Math.max(Math.max(width, height), depth);

            // Normalize scale to fit in our view
            double normalizedScale = 200.0 / maxDimension;
            double modelScale = furniture.getScale() * normalizedScale;

            System.out.println("Model dimensions: " + width + " x " + height + " x " + depth);
            System.out.println("Center point: " + centerX + ", " + centerY + ", " + centerZ);
            System.out.println("Normalized scale: " + normalizedScale);

            // Second pass - apply material and transformations
            for (MeshView mesh : currentMeshViews) {
                PhongMaterial material = new PhongMaterial(Color.web(furniture.getColor()));
                material.setSpecularColor(Color.WHITE);
                material.setSpecularPower(32);
                mesh.setMaterial(material);

                // Apply scale
                mesh.setScaleX(modelScale);
                mesh.setScaleY(modelScale);
                mesh.setScaleZ(modelScale);

                // Center the model
                mesh.setTranslateX(-centerX * modelScale);
                mesh.setTranslateY(-centerY * modelScale);
                mesh.setTranslateZ(-centerZ * modelScale);

                modelMeshGroup.getChildren().add(mesh);
            }

            // Add the mesh group to the model group
            modelGroup.getChildren().add(modelMeshGroup);

            // Log successful load
            System.out.println("Successfully loaded model: " + furniture.getModelPath());
            System.out.println("Number of meshes: " + currentMeshViews.length);

            // Add a bounding box for debugging (optional)
            if (showDebugBoundingBox) {
                Box boundingBox = new Box(width * modelScale, height * modelScale, depth * modelScale);
                boundingBox.setTranslateX(0);
                boundingBox.setTranslateY(0);
                boundingBox.setTranslateZ(0);
                boundingBox.setMaterial(new PhongMaterial(Color.TRANSPARENT));
                boundingBox.setDrawMode(DrawMode.LINE);
                boundingBox.setId("boundingBox");
                modelGroup.getChildren().add(boundingBox);
            }

        } catch (Exception e) {
            String errorMessage = "Error loading model: " + furniture.getModelPath() + "\n" + e.getMessage();
            System.err.println(errorMessage);
            e.printStackTrace();
            showModelError(errorMessage);
        }
    }

    private static void showModelError(String errorMessage) {
        // Create error message display
        Label errorLabel = new Label("Model Load Error");
        errorLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
        errorLabel.setTextFill(Color.RED);

        TextArea errorDetails = new TextArea(errorMessage);
        errorDetails.setEditable(false);
        errorDetails.setPrefHeight(100);
        errorDetails.setStyle("-fx-text-fill: red; -fx-control-inner-background: #fff8f8;");

        Button checkPathButton = new Button("Check Model Path");
        checkPathButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Model Path Information");
            alert.setHeaderText("Model Path Structure");
            alert.setContentText("Please ensure your model files are located in:\n" +
                    "src/main/resources/assets/models/\n\n" +
                    "Current project directory: " + System.getProperty("user.dir"));
            alert.showAndWait();
        });

        VBox errorBox = new VBox(10, errorLabel, errorDetails, checkPathButton);
        errorBox.setAlignment(Pos.CENTER);
        errorBox.setPadding(new Insets(20));
        errorBox.setMaxWidth(500);
        errorBox.setMaxHeight(250);
        errorBox.setStyle("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 5px;");

        // Add the error box to the model group
        modelGroup.getChildren().add(errorBox);
    }

    private static String getResourcePath(String relativePath) {
        File file = new File("src/main/resources/" + relativePath);
        return file.toURI().toString();
    }

    // Helper class for color indicator
    private static class Circle extends javafx.scene.shape.Circle {
        public Circle(double radius) {
            super(radius);
        }
    }
}