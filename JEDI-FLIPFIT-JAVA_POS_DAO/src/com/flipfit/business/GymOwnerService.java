package com.flipfit.business;

import com.flipfit.bean.GymOwner;
import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Role;
import com.flipfit.constants.GymStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service implementation for gym owners to manage their account and gym centers.
 */
public class GymOwnerService implements GymOwnerInterface {

    @Override
    public void registerOwner(String name, String email, String phoneNumber, String password) {
        // Prepare a new owner profile with unique IDs
        GymOwner owner = new GymOwner();
        String uniqueId = UUID.randomUUID().toString();

        owner.setUserId(uniqueId);
        owner.setOwnerId(UUID.randomUUID().toString());
        owner.setName(name);
        owner.setEmail(email);
        owner.setPhoneNumber(phoneNumber);
        owner.setPasswordHash(password);

        // Assign the 'GymOwner' role to the profile
        Role role = new Role();
        role.setRoleName("GymOwner");
        owner.setRole(role);

        // Note: New owners are usually 'Pending' until Admin approves them
        System.out.println("Registration request sent for owner: " + owner.getName());
    }

    @Override
    public void requestGymAddition(GymCenter gym) {
        // Set the gym status to pending so it waits for admin approval
        gym.setGymStatus(GymStatus.PENDING);

        // Save the new gym details to the database via DAO
        System.out.println("Gym addition request raised for: " + gym.getGymName());
    }

    @Override
    public void requestGymRemoval(String gymId) {
        // Send a request to the system to remove a specific gym center
        System.out.println("Requesting removal for gym ID: " + gymId);
    }

    @Override
    public List<GymCenter> viewMyGyms(String ownerId) {
        // Fetch and return all gyms belonging to this specific owner
        GymCenter tempGym = new GymCenter();
        tempGym.setGymId("123");
        tempGym.setGymLocation("Bangalore");
        tempGym.setGymName("Demo Gym");

        List<GymCenter> tempList = new ArrayList<>();
        tempList.add(tempGym);
        return tempList;
    }
}