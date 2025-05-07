package com.furnituredesign.models;

import java.util.ArrayList;
import java.util.List;

public class Design {
    private String name;
    private Room room;
    private List<Furniture> furnitureList;

    public Design(String name, Room room) {
        this.name = name;
        this.room = room;
        this.furnitureList = new ArrayList<>();
    }

    public void addFurniture(Furniture furniture) {
        this.furnitureList.add(furniture);
    }

    public void removeFurniture(Furniture furniture) {
        this.furnitureList.remove(furniture);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Furniture> getFurnitureList() {
        return furnitureList;
    }

    public void setFurnitureList(List<Furniture> furnitureList) {
        this.furnitureList = furnitureList;
    }
}
