package com.furnituredesign.graphics;

import com.furnituredesign.models.Design;
import com.furnituredesign.models.Furniture;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Renderer2D extends JPanel {
    private Design currentDesign;

    public Renderer2D(Design currentDesign) {
        this.currentDesign = currentDesign;
        setPreferredSize(new Dimension(600,400));
        setBackground(Color.WHITE);

    }

    public void setDesign(Design design) {
        this.currentDesign = design;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (currentDesign == null) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.LIGHT_GRAY);

        // Draw Room as rectangle
        int roomW = (int) currentDesign.getRoom().getWidth() * 50;
        int roomH = (int) currentDesign.getRoom().getDepth() * 50;

        g2d.drawRect(50, 50, roomW, roomH);

        // Draw furniture items as simple shapes
        List<Furniture> furnitureList = currentDesign.getFurnitureList();
        int xOffset = 60;
        int yOffset = 60;
        for (Furniture f : furnitureList) {
            g2d.setColor(Color.decode(f.getColor()));
            g2d.fillRect(xOffset, yOffset, 40, 40);
            g2d.setColor(Color.BLACK);
            g2d.drawString(f.getType(), xOffset, yOffset + 55);

            xOffset += 50;
            if (xOffset > roomW) {
                xOffset = 60;
                yOffset += 70;
            }
        }
    }

}
