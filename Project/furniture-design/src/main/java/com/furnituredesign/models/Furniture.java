package com.furnituredesign.models;

public class Furniture {
    private String type;
    private String modelPath;
    private String color;
    private double scale;

    public Furniture(String type, String modelPath, String color, double scale) {
        this.type = type;
        this.modelPath = modelPath;
        this.color = color;
        this.scale = scale;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModelPath() {
        return modelPath;
    }

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }
}
