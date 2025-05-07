package com.furnituredesign.models;

public class Room {
    private double width;
    private double height;
    private double depth;
    private String shape;
    private String colorScheme;


    public Room(double width, double height, double depth, String shape, String colorScheme) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.shape = shape;
        this.colorScheme = colorScheme;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public String getColorScheme() {
        return colorScheme;
    }

    public void setColorScheme(String colorScheme) {
        this.colorScheme = colorScheme;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }
}
