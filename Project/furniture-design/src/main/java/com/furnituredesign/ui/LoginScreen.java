package com.furnituredesign.ui;

import com.furnituredesign.models.User;
import com.furnituredesign.services.AuthService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginScreen {

    public static Scene getLoginScene(Stage primaryStage) {
        AuthService authService = new AuthService();

        // Title
        Label titleLabel = new Label("Furniture Design - Designer Login");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        VBox titleBox = new VBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(10, 0, 20, 0));

        // Fields
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(250);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(250);

        // Error message
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

        // Button
        Button loginButton = new Button("Login");
        loginButton.setStyle(
                "-fx-background-color: #3498db;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 8 20;" +
                        "-fx-background-radius: 6;"
        );
        loginButton.setOnMouseEntered(e -> loginButton.setStyle(
                "-fx-background-color: #2980b9;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 8 20;" +
                        "-fx-background-radius: 6;"
        ));
        loginButton.setOnMouseExited(e -> loginButton.setStyle(
                "-fx-background-color: #3498db;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 8 20;" +
                        "-fx-background-radius: 6;"
        ));

        // Action
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (authService.login(username, password)) {
                User loggedInUser = authService.getUser(username);
                primaryStage.setScene(DashboardScreen.getDashboardScene(primaryStage));
            } else {
                errorLabel.setText("Invalid credentials. Please try again.");
            }
        });

        VBox formBox = new VBox(10, usernameField, passwordField, loginButton, errorLabel);
        formBox.setAlignment(Pos.CENTER);

        VBox mainLayout = new VBox(15, titleBox, formBox);
        mainLayout.setPadding(new Insets(30));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #ffffff, #ecf0f1);");

        return new Scene(mainLayout, 450, 350);
    }
}
