package com.flipfit.dao;

import com.flipfit.bean.GymOwner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Access implementation for managing Gym Owners in memory.
 */
public class GymOwnerDaoImpl implements GymOwnerDaoInterface {

    // Using a Map to store owner data for fast lookups by their unique ID
    private static final Map<String, GymOwner> ownerMap = new HashMap<>();

    @Override
    public void addGymOwner(GymOwner owner) {
        // Save a new gym owner record into the internal map
        ownerMap.put(owner.getOwnerId(), owner);
        System.out.println("DAO: Gym Owner registered: " + owner.getOwnerId());
    }

    @Override
    public GymOwner getOwnerById(String ownerId) {
        // Retrieve a specific gym owner's profile using their unique ID
        return ownerMap.get(ownerId);
    }

    @Override
    public GymOwner getOwnerByEmail(String email) {
        // Search through all owners to find one that matches the given email
        return ownerMap.values().stream()
                .filter(owner -> owner.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<GymOwner> getAllOwners() {
        // Return a full list of all registered gym owners
        return new ArrayList<>(ownerMap.values());
    }

    @Override
    public void deleteOwner(String ownerId) {
        // Remove a gym owner from the system using their unique ID
        if (ownerMap.containsKey(ownerId)) {
            ownerMap.remove(ownerId);
            System.out.println("DAO: Gym Owner " + ownerId + " removed.");
        }
    }
}