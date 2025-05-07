package com.furnituredesign.graphics;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Rendered3D extends Application {

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();

        // Create a simple furniture cube
        Box box = new Box(100, 100, 100);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.DARKRED);
        box.setMaterial(material);

        box.setTranslateX(250);
        box.setTranslateY(150);
        box.setTranslateZ(400);

        // Add rotation for 3D effect
        box.getTransforms().addAll(
                new Rotate(20, Rotate.X_AXIS),
                new Rotate(45, Rotate.Y_AXIS)
        );

        root.getChildren().add(box);

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1000);

        Scene scene = new Scene(root, 600, 400, true);
        scene.setFill(Color.LIGHTGRAY);
        scene.setCamera(camera);

        primaryStage.setTitle("3D Furniture Viewer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void launch3D() {
        launch();
    }
}
