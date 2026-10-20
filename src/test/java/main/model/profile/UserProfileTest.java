package main.model.profile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserProfileTest {

    @Test
    void profileShouldStartWithNoLastSlotAndNoPreferences() {

        UserProfile profile = new UserProfile();

        assertEquals("-1", profile.getLastPlayedSlotId());
        assertTrue(profile.getPreferences().isEmpty());
    }

    @Test
    void preferencesShouldBeEnabledAndDisabled() {

        UserProfile profile = new UserProfile();

        profile.enablePreference(
                Preference.AUTOSAVE
        );

        assertTrue(
                profile.hasPreference(
                        Preference.AUTOSAVE
                )
        );

        profile.disablePreference(
                Preference.AUTOSAVE
        );

        assertFalse(
                profile.hasPreference(
                        Preference.AUTOSAVE
                )
        );
    }

    @Test
    void preferenceConversionShouldMapMenuValues() {

        assertEquals(
                Preference.AUTOSAVE,
                Preference.toPreference(1)
        );

        assertEquals(
                Preference.SHOW_COMMAND_HUD,
                Preference.toPreference(2)
        );

        assertEquals(
                Preference.SHOW_PLAYER_HUD,
                Preference.toPreference(3)
        );

        assertEquals(
                Preference.DEFAULT,
                Preference.toPreference(99)
        );
    }

    @Test
    void lastPlayedSlotShouldBeUpdated() {

        UserProfile profile = new UserProfile();

        profile.setLastPlayedSlotId("3");

        assertEquals(
                "3",
                profile.getLastPlayedSlotId()
        );
    }
}
