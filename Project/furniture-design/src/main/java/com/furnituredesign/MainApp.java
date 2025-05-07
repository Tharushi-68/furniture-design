package com.furnituredesign;

import com.furnituredesign.ui.LoginScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Furniture Design App");
        primaryStage.setScene(LoginScreen.getLoginScene(primaryStage));
        primaryStage.show();
    }
}
