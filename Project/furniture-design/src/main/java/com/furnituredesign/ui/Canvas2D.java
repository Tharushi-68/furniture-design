package com.furnituredesign.ui;

import com.furnituredesign.models.FurnitureItemOnCanvas;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;



public class Canvas2D {

    private static final Canvas canvas = new Canvas(600, 600);
    private static final GraphicsContext gc = canvas.getGraphicsContext2D();
    private static final List<FurnitureItemOnCanvas> furnitureOnCanvas = new ArrayList<>();

    private static FurnitureItemOnCanvas draggedItem = null;
    private static double offsetX, offsetY;

    // Room properties
    private static double roomX = 50, roomY = 50;
    private static double roomWidth = 300, roomHeight = 400;
    private static Color roomColor = Color.LIGHTGRAY;
    private static Color wallColor = Color.BEIGE;
    private static String roomShape = "rectangle"; // "oval" supported

    // Reference to Canvas3D singleton
    private static Canvas3D canvas3D = Canvas3D.getInstance();

    /**
     * Returns a StackPane with the 2D Canvas and sets up mouse interactions.
     */
    public static StackPane get2DCanvas() {
        drawRoom();

        canvas.setOnMousePressed(Canvas2D::handleMousePressed);
        canvas.setOnMouseDragged(Canvas2D::handleMouseDragged);
        canvas.setOnMouseReleased(e -> draggedItem = null);

        return new StackPane(canvas);
    }

    /**
     * Adds a new furniture item with calculated offset position.
     */
    public static void addFurnitureToCanvas(String name) {
        double x = roomX + 10 + furnitureOnCanvas.size() * 10;
        double y = roomY + 10 + furnitureOnCanvas.size() * 10;
        furnitureOnCanvas.add(new FurnitureItemOnCanvas(name, x, y));
        drawRoom();
    }

    /**
     * Updates the room's properties and redraws it.
     */
    public static void updateRoomProperties(double width, double height, Color floorColor, Color newWallColor, String shape) {
        roomWidth = width;
        roomHeight = height;
        roomColor = floorColor;
        wallColor = newWallColor;
        roomShape = shape.toLowerCase();
        drawRoom();
    }

    private static void handleMousePressed(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        // Check from top to bottom (last drawn is on top)
        for (int i = furnitureOnCanvas.size() - 1; i >= 0; i--) {
            FurnitureItemOnCanvas item = furnitureOnCanvas.get(i);
            if (item.contains(x, y)) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    draggedItem = item;
                    offsetX = x - item.getX();
                    offsetY = y - item.getY();
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    furnitureOnCanvas.remove(item);
                    drawRoom();
                }
                break;
            }
        }
    }

    private static void handleMouseDragged(MouseEvent event) {
        if (draggedItem != null) {
            draggedItem.setX(event.getX() - offsetX);
            draggedItem.setY(event.getY() - offsetY);
            drawRoom();
        }
    }

    /**
     * Redraws the entire room and its contents.
     */
    private static void drawRoom() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw room shape
        gc.setFill(roomColor);
        gc.setStroke(Color.BLACK);
        if ("oval".equals(roomShape)) {
            gc.fillOval(roomX, roomY, roomWidth, roomHeight);
            gc.strokeOval(roomX, roomY, roomWidth, roomHeight);
        } else {
            gc.fillRect(roomX, roomY, roomWidth, roomHeight);
            gc.strokeRect(roomX, roomY, roomWidth, roomHeight);
        }

        // Draw each furniture item
        for (FurnitureItemOnCanvas item : furnitureOnCanvas) {
            gc.setFill(Color.SADDLEBROWN);
            gc.fillRect(item.getX(), item.getY(), 40, 30);
            gc.setFill(Color.BLACK);
            gc.fillText(item.getName(), item.getX(), item.getY() - 5);
        }

        // Sync with 3D Canvas - using singleton instance methods
        canvas3D.updateRoom(roomWidth, roomHeight, roomColor, wallColor);
        canvas3D.setFurniture(furnitureOnCanvas);
    }

    /**
     * Saves the design as JSON to a file.
     */
    public static void saveDesignToFile(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Color.class, new ColorSerializer())
                    .setPrettyPrinting()
                    .create();

            DesignData data = new DesignData(
                    furnitureOnCanvas, roomWidth, roomHeight, roomColor, wallColor, roomShape
            );

            gson.toJson(data, writer);
            System.out.println("Design saved successfully to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Resets the canvas and room to default state.
     */
    public static void resetDesign() {
        furnitureOnCanvas.clear();
        roomWidth = 300;
        roomHeight = 400;
        roomColor = Color.LIGHTGRAY;
        wallColor = Color.BEIGE;
        roomShape = "rectangle";
        drawRoom();
    }

    /**
     * Class that wraps the entire design data for saving/loading.
     */
    private static class DesignData {
        List<FurnitureItemOnCanvas> furniture;
        double width, height;
        Color floorColor, wallColor;
        String shape;

        DesignData(List<FurnitureItemOnCanvas> furniture, double width, double height,
                   Color floorColor, Color wallColor, String shape) {
            this.furniture = furniture;
            this.width = width;
            this.height = height;
            this.floorColor = floorColor;
            this.wallColor = wallColor;
            this.shape = shape;
        }
    }

    /**
     * Custom Gson serializer for JavaFX Color.
     */
    private static class ColorSerializer implements JsonSerializer<Color> {
        @Override
        public JsonElement serialize(Color color, Type type, JsonSerializationContext context) {
            JsonObject json = new JsonObject();
            json.addProperty("red", color.getRed());
            json.addProperty("green", color.getGreen());
            json.addProperty("blue", color.getBlue());
            json.addProperty("opacity", color.getOpacity());
            return json;
        }
    }
}