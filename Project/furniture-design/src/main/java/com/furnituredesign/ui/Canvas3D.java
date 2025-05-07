package com.furnituredesign.ui;

import com.furnituredesign.models.Furniture;
import com.furnituredesign.models.FurnitureItemOnCanvas;
import com.furnituredesign.services.FurnitureService;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Canvas3D {

    // Remove static from these fields - create new instances each time
    private Group root3D;
    private Group sceneGroup;
    private Box floor3D, backWall, leftWall, rightWall;
    private final List<Node> furniture3DNodes = new ArrayList<>();

    // Interaction state
    private double anchorX, anchorY;
    private double angleX = -30, angleY = -45;
    private final Rotate rotateX = new Rotate(angleX, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(angleY, Rotate.Y_AXIS);
    private final Scale worldScale = new Scale(1,1,1);

    // Selection & handle
    private Box selectedFurniture = null;
    private Scale selectedFurnitureScale = null;

    // Create a singleton instance approach
    private static Canvas3D instance;

    public static Canvas3D getInstance() {
        if (instance == null) {
            instance = new Canvas3D();
        }
        return instance;
    }

    // Private constructor to enforce singleton pattern
    private Canvas3D() {
        root3D = new Group();
        sceneGroup = new Group();
    }

    public StackPane get3DView() {
        root3D = new Group(); // Create new instances instead of reusing
        sceneGroup = new Group();
        furniture3DNodes.clear();
        selectedFurniture = null;
        selectedFurnitureScale = null;

        // Room Dimensions
        double width = 400, depth = 600, wallHeight = 100, thickness = 5;

        // Floor
        floor3D = new Box(width, 5, depth);
        floor3D.setMaterial(new PhongMaterial(Color.LIGHTGRAY));
        floor3D.setTranslateY(2.5);

        // Back Wall
        backWall = new Box(width, wallHeight, thickness);
        backWall.setMaterial(new PhongMaterial(Color.BEIGE));
        backWall.setTranslateZ(-depth / 2 + thickness / 2);
        backWall.setTranslateY(-wallHeight / 2 + 2.5);

        // Left Wall
        leftWall = new Box(thickness, wallHeight, depth);
        leftWall.setMaterial(new PhongMaterial(Color.BEIGE));
        leftWall.setTranslateX(-width / 2 + thickness / 2);
        leftWall.setTranslateY(-wallHeight / 2 + 2.5);

        // Right Wall
        rightWall = new Box(thickness, wallHeight, depth);
        rightWall.setMaterial(new PhongMaterial(Color.BEIGE));
        rightWall.setTranslateX(width / 2 - thickness / 2);
        rightWall.setTranslateY(-wallHeight / 2 + 2.5);

        // Add elements to scene group
        sceneGroup.getChildren().addAll(floor3D, backWall, leftWall, rightWall);
        sceneGroup.getTransforms().addAll(rotateX, rotateY, worldScale);

        // Lighting
        PointLight pointLight = new PointLight(Color.WHITE);
        pointLight.getTransforms().add(new Translate(0, -300, -400));
        AmbientLight ambientLight = new AmbientLight(Color.rgb(180, 180, 180));
        root3D.getChildren().addAll(sceneGroup, pointLight, ambientLight);

        // SubScene setup
        SubScene subScene = new SubScene(root3D, 1000, 700, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.LIGHTBLUE);
        subScene.setCamera(setupCamera());

        // Enable controls
        enableMouseControl(subScene);
        enableZoomControl(subScene);
        enableKeyboardReset(subScene);
        subScene.setFocusTraversable(true); // Important for keyboard events

        return new StackPane(subScene);
    }


    private Camera setupCamera() {
        PerspectiveCamera cam = new PerspectiveCamera(true);
        cam.getTransforms().add(new Translate(0,0,-900));
        cam.setNearClip(0.1);
        cam.setFarClip(5000);
        return cam;
    }

    private void enableMouseControl(SubScene s) {
        s.setOnMousePressed(e -> {
            anchorX = e.getSceneX(); anchorY = e.getSceneY();
        });
        s.setOnMouseDragged(e -> {
            double dx = e.getSceneX()-anchorX, dy = e.getSceneY()-anchorY;
            angleY += dx*0.3; angleX -= dy*0.3;
            rotateX.setAngle(angleX); rotateY.setAngle(angleY);
            anchorX = e.getSceneX(); anchorY = e.getSceneY();
        });
    }

    private void enableZoomControl(SubScene s) {
        s.setOnScroll((ScrollEvent e)->{
            double f = e.getDeltaY()<0?0.9:1.1;
            worldScale.setX(worldScale.getX()*f);
            worldScale.setY(worldScale.getY()*f);
            worldScale.setZ(worldScale.getZ()*f);
        });
    }

    private void enableKeyboardReset(SubScene s) {
        s.setOnKeyPressed(e -> {
            if (e.getCode()==KeyCode.R) {
                worldScale.setX(1); worldScale.setY(1); worldScale.setZ(1);
                angleX=-30; angleY=-45;
                rotateX.setAngle(angleX); rotateY.setAngle(angleY);
            }
        });
        s.setFocusTraversable(true);
    }

    /** Update room floor & walls, then fit to view */
    public void updateRoom(double width, double depth, Color floorColor, Color wallColor){
        if(floor3D==null) return;
        floor3D.setWidth(width); floor3D.setDepth(depth);
        floor3D.setMaterial(new PhongMaterial(floorColor));
        PhongMaterial wm=new PhongMaterial(wallColor);
        double t=backWall.getDepth(), h=backWall.getHeight();
        backWall.setWidth(width); backWall.setTranslateZ(-depth/2+t/2);
        backWall.setMaterial(wm); backWall.setTranslateY(-h/2+2.5);
        leftWall.setDepth(depth); leftWall.setTranslateX(-width/2+t/2);
        leftWall.setMaterial(wm); leftWall.setTranslateY(-h/2+2.5);
        rightWall.setDepth(depth); rightWall.setTranslateX(width/2-t/2);
        rightWall.setMaterial(wm); rightWall.setTranslateY(-h/2+2.5);
        fitToRoom();
    }

    /** Uniformly scale so room fits */
    private void fitToRoom(){
        double max= Math.max(floor3D.getWidth(),floor3D.getDepth());
        double factor = 400.0/max;
        worldScale.setX(factor); worldScale.setY(factor); worldScale.setZ(factor);
    }

    /** Place furniture and attach selection handler */
    public void setFurniture(List<FurnitureItemOnCanvas> list){
        sceneGroup.getChildren().removeAll(furniture3DNodes);
        furniture3DNodes.clear();

        FurnitureService service = new FurnitureService();

        for (FurnitureItemOnCanvas it: list) {
            Furniture furniture = service.getAvailableFurniture().stream()
                    .filter(f -> f.getType().equals(it.getName()))
                    .findFirst()
                    .orElse(null);

            if (furniture == null) continue;

            // Create Box
            Box box = new Box(40 * it.getScale(), 30 * it.getScale(), 20 * it.getScale());
            Color color = Color.web(furniture.getColor(), 1.0);
            box.setMaterial(new PhongMaterial(color));
            box.setTranslateX(it.getX() - 200);
            box.setTranslateZ(it.getY() - 300);
            box.setTranslateY(-15 * it.getScale());

            // Attach scale transform
            Scale s = new Scale(it.getScale(), it.getScale(), it.getScale());
            box.getTransforms().add(s);

            // Click to select
            box.setOnMouseClicked(evt -> {
                selectedFurniture = box;
                selectedFurnitureScale = s;
                evt.consume();
            });

            // Label above box
            Text label = new Text(furniture.getType());
            label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            label.setFill(Paint.valueOf("black"));
            label.setTranslateX(box.getTranslateX() - 30);
            label.setTranslateZ(box.getTranslateZ());
            label.setTranslateY(box.getTranslateY() - 25); // above the box

            // Add to scene
            furniture3DNodes.add(box);
            furniture3DNodes.add(label);
        }

        sceneGroup.getChildren().addAll(furniture3DNodes);
    }

    public void setSelectedScale(double newScale) {
        if (selectedFurnitureScale != null && newScale > 0.1) {
            selectedFurnitureScale.setX(newScale);
            selectedFurnitureScale.setY(newScale);
            selectedFurnitureScale.setZ(newScale);
        }
    }
}