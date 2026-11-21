package main.facade;

import main.controller.GameController;

public class GameFacade {

    private final GameController gameController;

    public GameFacade(GameController gameController) {
        this.gameController = gameController;
    }

    public GameFlowResult chooseSceneOption(int choiceIndex) {

        autosaveIfEnabled();

        gameController.applySceneChoiceConsequence(choiceIndex);

        return continueNarrative();
    }

    public GameFlowResult chooseFinalOption(int choiceIndex) {

        autosaveIfEnabled();

        gameController.applyChapterFinalChoiceConsequence(choiceIndex);

        if (gameController.isGameOver()) {
            return GameFlowResult.GAME_OVER;
        }

        return finishChapter();
    }

    public GameFlowResult continueNarrative() {

        if (gameController.isGameOver()) {
            return GameFlowResult.GAME_OVER;
        }

        if (gameController.hasNextScene()) {

            gameController.advanceToNextScene();

            return GameFlowResult.SCENE_UPDATED;
        }

        if (gameController.hasAvailableChapterFinalChoices()) {
            return GameFlowResult.FINAL_CHOICES_AVAILABLE;
        }

        return finishChapter();
    }

    public GameFlowResult finishChapter() {

        if (gameController.isFinalChapter()) {

            gameController.setGameIsCompleted();

            gameController.saveGame();

            return GameFlowResult.GAME_COMPLETED;
        }

        return GameFlowResult.CHAPTER_FINISHED;
    }

    public void saveCurrentGame() {
        gameController.saveGame();
    }

    private void autosaveIfEnabled() {

        if (gameController.isAutosaveEnabled()) {
            saveCurrentGame();
        }
    }
}
