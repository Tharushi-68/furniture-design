package com.furnituredesign.services;

import com.furnituredesign.models.Design;
import com.furnituredesign.models.Furniture;
import com.furnituredesign.models.Room;

import java.util.ArrayList;
import java.util.List;

public class DesignService {
    private List<Design> savedDesigns = new ArrayList<>();

    public Design createDesign(String name, Room room) {
        return new Design(name, room);
    }

    public void saveDesign(Design design) {
        savedDesigns.add(design);
    }

    public void deleteDesign(Design design) {
        savedDesigns.remove(design);
    }

    public List<Design> getAllDesigns() {
        return savedDesigns;
    }

    public void addFurnitureToDesign(Design design, Furniture furniture) {
        design.addFurniture(furniture);
    }

    public void removeFurnitureFromDesign(Design design, Furniture furniture) {
        design.removeFurniture(furniture);
    }
}
