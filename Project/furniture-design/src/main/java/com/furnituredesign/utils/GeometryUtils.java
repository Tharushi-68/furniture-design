package com.furnituredesign.utils;

public class GeometryUtils {
    public static double[] scaleToFit(double width, double height, double maxWidth, double maxHeight) {
        double scale = Math.min(maxWidth / width, maxHeight / height);
        return new double[] { width * scale, height * scale };
    }
    public static double distance2D(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
    public static double distance3D(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) +
                Math.pow(y2 - y1, 2) +
                Math.pow(z2 - z1, 2));
    }
    public static boolean fitsInside(double fWidth, double fDepth, double rWidth, double rDepth) {
        return fWidth <= rWidth && fDepth <= rDepth;
    }
}
