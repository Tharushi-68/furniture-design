package com.furnituredesign.services;

import com.furnituredesign.models.Furniture;

import java.util.ArrayList;
import java.util.List;

public class FurnitureService {
    private List<Furniture> catalog = new ArrayList<>();

    public FurnitureService() {
        // Add predefined furniture items (demo)
        catalog.add(new Furniture("Classic Sofa", "assets/models/class-sofa.obj", "Red", 1.0));
        catalog.add(new Furniture("Cushion Chair", "assets/models/cushion-chair.obj", "Blue", 1.0));
        catalog.add(new Furniture("Design Chair", "assets/models/design-chair.obj", "Green", 1.0));
        catalog.add(new Furniture("Modern Chair", "assets/models/modern-chair.obj", "Yellow", 1.0));
        catalog.add(new Furniture("Poly Chair", "assets/models/poly-chair.obj", "Orange", 1.0));
        catalog.add(new Furniture("School Chair", "assets/models/school-chair.obj", "Purple", 1.0));
        catalog.add(new Furniture("Modern Sofa", "assets/models/sofa.obj", "Beige", 1.0));
        catalog.add(new Furniture("Wood Sofa", "assets/models/class-sofa.obj", "Blue", 1.0));

    }

    public List<Furniture> getAvailableFurniture() {
        return catalog;
    }

    public void addFurnitureToCatalog(Furniture furniture) {
        catalog.add(furniture);
    }

    public void removeFurnitureFromCatalog(Furniture furniture) {
        catalog.remove(furniture);
    }
}