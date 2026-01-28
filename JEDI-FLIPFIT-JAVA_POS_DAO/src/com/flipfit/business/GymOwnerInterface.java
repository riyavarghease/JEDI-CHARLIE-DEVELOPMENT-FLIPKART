package com.flipfit.business;

import com.flipfit.bean.GymCenter;
import java.util.List;

/**
 * Interface for gym owners to manage their profile and gym centers.
 */
public interface GymOwnerInterface {

    // Register a new gym owner account in the system
    void registerOwner(String name, String email, String phoneNumber, String password);

    // Submit a request to add a new gym center for admin approval
    void requestGymAddition(GymCenter gym);

    // Submit a request to delete a gym center from the platform
    void requestGymRemoval(String gymId);

    // Get a list of all gym centers belonging to a specific owner
    List<GymCenter> viewMyGyms(String ownerId);
}