package com.flipfit.business;

import com.flipfit.bean.GymCenter;
import java.util.List;

public interface GymOwnerInterface {
    void registerOwner(String name, String email , String phoneNumber, String password, String panNumber, String aadharNumber);
    void requestGymAddition(GymCenter gym);
    void requestGymRemoval(String gymId);
    List<GymCenter> viewMyGyms(String ownerId);
}