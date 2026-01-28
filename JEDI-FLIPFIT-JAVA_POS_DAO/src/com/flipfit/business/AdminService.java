package com.flipfit.business;

import com.flipfit.bean.*;
import com.flipfit.dao.AdminDaoImpl;
import com.flipfit.dao.AdminDaoInterface;
import com.flipfit.dao.UserDaoImpl;
import com.flipfit.dao.UserDaoInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of Admin operations for the FlipFit system.
 */
public class AdminService implements AdminInterface {

    @Override
    public void registerAdmin(String name, String email, String phoneNumber, String password) {
        // Create unique identifiers for the user and admin records
        String userId = UUID.randomUUID().toString();
        String adminId = UUID.randomUUID().toString();

        // Assign the 'Admin' role to the new user
        Role adminRole = new Role();
        adminRole.setRoleName("Admin");

        // Populate the general User profile
        User user = new User();
        user.setUserId(userId);
        user.setName(name);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPasswordHash(password);
        user.setRole(adminRole);

        // Populate the Admin-specific details
        Admin admin = new Admin();
        admin.setUserId(userId);
        admin.setAdminId(adminId);
        admin.setName(name);
        admin.setEmail(email);
        admin.setPhoneNumber(phoneNumber);
        admin.setPasswordHash(password);
        admin.setRole(adminRole);

        // Save the profile to the central User database
        UserDaoInterface userDao = new UserDaoImpl();
        userDao.addUser(user);

        // Save the profile to the specific Admin database
        AdminDaoInterface adminDao = new AdminDaoImpl();
        adminDao.addAdmin(admin);

        System.out.println("Registration successful for Admin: " + name);
    }

    @Override
    public void approveGymCenter(String gymId) {
        // Change the status of a gym to 'Approved' in the database
        System.out.println("Admin approved Gym ID: " + gymId);
    }

    @Override
    public void approveGymOwner(String ownerId) {
        // Verify the gym owner to allow them full access to the platform
        System.out.println("Admin approved Owner ID: " + ownerId);
    }

    @Override
    public List<GymCenter> viewPendingGyms() {
        // Retrieve all gym centers currently waiting for admin validation
        GymCenter tempGym = new GymCenter();
        tempGym.setGymId("123");
        tempGym.setGymLocation("Bangalore");
        tempGym.setGymName("Demo Gym");
        List<GymCenter> tempList = new ArrayList<>();
        tempList.add(tempGym);
        return tempList;
    }

    @Override
    public List<GymOwner> viewPendingOwners() {
        // Retrieve all gym owners currently waiting for admin validation
        GymOwner owner = new GymOwner();
        owner.setOwnerId("1234");
        owner.setName("Demo owner");
        List<GymOwner> ownerList = new ArrayList<>();
        ownerList.add(owner);
        return ownerList;
    }
}