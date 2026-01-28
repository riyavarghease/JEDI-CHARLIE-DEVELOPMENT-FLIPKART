package com.flipfit.business;

import com.flipfit.bean.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Service implementation for shared user tasks like login and account management.
 */
public class UserService implements UserInterface {

    // Temporary list to hold user data before database connection
    private List<User> users = new ArrayList<>();

    @Override
    public void registerUser(User user) {
        // Add a new user profile to the collection
        users.add(user);
        System.out.println("User registered successfully: " + user.getName());
    }

    @Override
    public boolean login(String email, String password) {
        // Check every user to see if email and password match
        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPasswordHash().equals(password)) {
                System.out.println("Login successful for: " + u.getName());
                return true;
            }
        }
        return false;
    }

    @Override
    public void updatePassword(String userId, String newPassword) {
        // Find the user by ID and replace their old password
        for (User u : users) {
            if (u.getUserId().equals(userId)) {
                u.setPasswordHash(newPassword);
                System.out.println("Password updated.");
            }
        }
    }

    @Override
    public User getUserDetails(String userId) {
        // Search and return the full profile for a specific user ID
        return users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }
}