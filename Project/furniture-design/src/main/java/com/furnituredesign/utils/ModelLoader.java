package com.furnituredesign.utils;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.transform.Scale;

public class ModelLoader {

    public static Group loadModel(String modelPath, double scaleFactor) {
        ObjModelImporter importer = new ObjModelImporter();
        importer.read(modelPath);
        Node[] nodes = importer.getImport();
        importer.close();

        Group modelGroup = new Group(nodes);
        modelGroup.getTransforms().add(new Scale(scaleFactor, scaleFactor, scaleFactor));


        return modelGroup;
    }
}
