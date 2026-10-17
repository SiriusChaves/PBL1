package main.service.profile;

import main.persistence.dao.UserProfileDao;
import main.model.profile.Preference;
import main.model.profile.UserProfile;

public class UserProfileService {

    private final UserProfileDao profileDao;

    public UserProfileService(UserProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    public UserProfile loadUserProfile() {
        return profileDao.loadUserProfile();
    }

    public void setLastPlayedSlotId(String slotIndex) {

        UserProfile profile = loadUserProfile();

        profile.setLastPlayedSlotId(slotIndex);

        saveProfile(profile);
    }

    public void togglePreference(Preference preference) {
        UserProfile profile = loadUserProfile();

        if (profile.hasPreference(preference)) {

            profile.disablePreference(preference);
        } else {

            profile.enablePreference(preference);
        }

        saveProfile(profile);
    }

    private void saveProfile(UserProfile userProfile) {
        profileDao.save(userProfile);
    }
}
