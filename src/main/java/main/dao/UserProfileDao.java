package main.dao;

import main.exception.UserProfileErrorException;
import main.model.UserProfile;

public interface UserProfileDao {
    void save(UserProfile userProfile) throws UserProfileErrorException;
    UserProfile loadUserProfile() throws UserProfileErrorException;
}
