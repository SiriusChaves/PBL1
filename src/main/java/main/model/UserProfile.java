package main.model;

import java.util.EnumSet;

public class UserProfile {
    private String lastPlayedSlotId;
    private EnumSet<Preference> preferences;

    public UserProfile() {
        lastPlayedSlotId = "-1";
        preferences = EnumSet.noneOf(Preference.class);
    }

    public void enablePreference(Preference preference) {
        preferences.add(preference);
    }

    public boolean hasPreference(Preference preference) {
        return preferences.contains(preference);
    }

    public void disablePreference(Preference preference) {
        preferences.remove(preference);
    }

    public String getLastPlayedSlotId() {
        return lastPlayedSlotId;
    }

    public EnumSet<Preference> getPreferences() {
        return preferences;
    }

    public void setLastPlayedSlotId(String lastPlayedSlotId) {
        this.lastPlayedSlotId = lastPlayedSlotId;
    }

}
