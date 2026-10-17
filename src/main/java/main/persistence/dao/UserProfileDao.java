package main.persistence.dao;

import main.exception.UserProfileErrorException;
import main.model.profile.UserProfile;

public interface UserProfileDao {
    void save(UserProfile userProfile) throws UserProfileErrorException;
    UserProfile loadUserProfile() throws UserProfileErrorException;
}
