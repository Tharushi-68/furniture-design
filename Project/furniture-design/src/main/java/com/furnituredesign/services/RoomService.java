package com.furnituredesign.services;


import com.furnituredesign.models.Room;

public class RoomService {

    public Room createRoom(double width, double height, double depth, String shape, String colorScheme) {
        return new Room(width, height, depth, shape, colorScheme);
    }

    public void updateRoom(Room room, double width, double height, double depth, String shape, String colorScheme) {
        room.setWidth(width);
        room.setHeight(height);
        room.setDepth(depth);
        room.setShape(shape);
        room.setColorScheme(colorScheme);
    }
}
