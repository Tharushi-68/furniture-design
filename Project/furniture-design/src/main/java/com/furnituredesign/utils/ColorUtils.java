package com.furnituredesign.utils;

import java.awt.*;

public class ColorUtils {
    public static Color hexToColor(String hex) {
        return Color.decode(hex);
    }

    // Convert Color to hex string
    public static String colorToHex(Color color) {
        return String.format("#%02x%02x%02x",
                color.getRed(),
                color.getGreen(),
                color.getBlue());
    }

    // Adjust brightness: factor > 1 = brighter, 0 < factor < 1 = darker
    public static Color adjustBrightness(Color color, float factor) {
        int r = Math.min(255, Math.round(color.getRed() * factor));
        int g = Math.min(255, Math.round(color.getGreen() * factor));
        int b = Math.min(255, Math.round(color.getBlue() * factor));
        return new Color(r, g, b);
    }

    // Blend two colors together
    public static Color blendColors(Color c1, Color c2, float ratio) {
        float r = ratio;
        float ir = 1.0f - r;

        int red = (int)(c1.getRed() * r + c2.getRed() * ir);
        int green = (int)(c1.getGreen() * r + c2.getGreen() * ir);
        int blue = (int)(c1.getBlue() * r + c2.getBlue() * ir);

        return new Color(red, green, blue);
    }
}
