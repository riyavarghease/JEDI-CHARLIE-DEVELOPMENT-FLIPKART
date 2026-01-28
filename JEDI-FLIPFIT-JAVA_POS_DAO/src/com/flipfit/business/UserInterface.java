package com.flipfit.business;

import com.flipfit.bean.User;

/**
 * Interface for general user operations and account security.
 */
public interface UserInterface {

    // Save a new user's information into the system
    void registerUser(User user);

    // Check user credentials to grant access to the platform
    boolean login(String email, String password);

    // Change an existing user's password for better security
    void updatePassword(String userId, String newPassword);

    // Retrieve all profile information for a specific user ID
    User getUserDetails(String userId);
}