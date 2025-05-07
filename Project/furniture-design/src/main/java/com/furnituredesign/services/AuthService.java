package com.furnituredesign.services;

import com.furnituredesign.models.User;

import java.util.HashMap;

public class AuthService {
    private HashMap<String, User> users = new HashMap<>();
    public AuthService() {
        users.put("designer1", new User("designer1", "1234", "designer"));
        users.put("admin", new User("admin", "admin", "admin"));
    }

    public boolean login(String username, String password) {
        User user = users.get(username);
        if (user != null) {
            return user.authenticate(password);
        }
        return false;
    }

    public User getUser(String username) {
        return users.get(username);
    }
}
