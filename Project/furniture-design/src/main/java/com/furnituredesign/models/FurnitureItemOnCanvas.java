package com.furnituredesign.models;

import javafx.scene.paint.Color;

public class FurnitureItemOnCanvas {
    private String name;
    private double x;
    private double y;
    private double scale;
    private Color color;// Uniform scale factor for 3D rendering

    // Default constructor sets scale = 1.0
    public FurnitureItemOnCanvas(String name, double x, double y) {
        this(name, x, y, 1.0);
    }

    // Full constructor
    public FurnitureItemOnCanvas(String name, double x, double y, double scale) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getScale() {
        return scale;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    /**
     * Checks if the given canvas coordinates (px,py) fall within the
     * 2D bounding box of this furniture item (assumed 40×30).
     */
    public boolean contains(double px, double py) {
        return px >= x && px <= x + 40 * scale
                && py >= y && py <= y + 30 * scale;
    }

    public Color getColor() { return color; }
}
