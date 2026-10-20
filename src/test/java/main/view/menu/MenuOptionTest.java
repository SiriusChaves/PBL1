package main.view.menu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuOptionTest {

    @Test
    void mainMenuShouldMapValidAndInvalidValues() {

        assertEquals(
                MainMenuOption.START_GAME,
                MainMenuOption.toMainMenuOption(1)
        );

        assertEquals(
                MainMenuOption.EXIT,
                MainMenuOption.toMainMenuOption(6)
        );

        assertEquals(
                MainMenuOption.DEFAULT,
                MainMenuOption.toMainMenuOption(99)
        );
    }

    @Test
    void saveMenuShouldMapValidAndInvalidValues() {

        assertEquals(
                SaveMenuOption.CONTINUE,
                SaveMenuOption.toSaveMenuOption(1)
        );

        assertEquals(
                SaveMenuOption.NEW_GAME,
                SaveMenuOption.toSaveMenuOption(2)
        );

        assertEquals(
                SaveMenuOption.LOAD_GAME,
                SaveMenuOption.toSaveMenuOption(3)
        );

        assertEquals(
                SaveMenuOption.DEFAULT,
                SaveMenuOption.toSaveMenuOption(99)
        );
    }
}
