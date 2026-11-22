
package main.persistence;

import java.nio.file.Path;

public final class StoragePaths {

    private static final Path DATA_DIRECTORY =
            Path.of(
                    System.getProperty("user.home"),
                    "MegaBrain",
                    "data"
            );

    private StoragePaths() {}

    public static Path getAchievementsFile() {
        return DATA_DIRECTORY.resolve(
                "achievements/achievements.json"
        );
    }

    public static Path getProfileFile() {
        return DATA_DIRECTORY.resolve(
                "profile/profile.json"
        );
    }

    public static Path getSaveFile(String slotIndex) {
        return DATA_DIRECTORY.resolve(
                "saves/slot" + slotIndex
                        + "/slot" + slotIndex + ".json"
        );
    }
}
