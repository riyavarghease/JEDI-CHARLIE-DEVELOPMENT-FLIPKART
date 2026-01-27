package com.flipkart.business;

import com.flipkart.bean.GymCenter;
import com.flipkart.bean.GymOwner;
import java.util.List;

public interface AdminInterface {
    void registerAdmin(String name, String email, String phoneNumber, String password);
    void approveGymCenter(String gymId);
    void approveGymOwner(String ownerId);
    List<GymCenter> viewPendingGyms();
    List<GymOwner> viewPendingOwners();
}