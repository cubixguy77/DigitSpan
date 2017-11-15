package com.mastersofmemory.flashnumbers;

import android.os.Bundle;
import android.os.Handler;

public class GameStateManager implements NumberFlashGameStateListener, SaveInstanceStateListener {

    private GameData data;

    GameStateManager(GameData data) {
        this.data = data;
        NumberFlashBus.getBus().subscribe(this);
    }

    @Override
    public void onPreMemorization(GameData data) {
        NumberFlashBus.gameState = GameState.PRE_MEMORIZATION;
    }

    @Override
    public void onMemorizationStart() {
        NumberFlashBus.gameState = GameState.MEMORIZATION;
    }

    @Override
    public void onRecallStart() {
        NumberFlashBus.gameState = GameState.RECALL;
    }

    @Override
    public void onRecallComplete(final NumberFlashResult result) {
        NumberFlashBus.gameState = GameState.REVIEW;

        if (!result.isCorrect()) {
            data.loseLife();
        }

        /* Game Over */
        if (data.getNumLivesRemaining() == 0) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    NumberFlashBus.getBus().onGameOver();
                }
            }, 1500);
        }

        /* Game continues */
        else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    data.resetTo(result.isCorrect() ? data.getNumDigitsToAttempt() + 1 : data.getNumDigitsToAttempt() - 1);
                    NumberFlashBus.getBus().onPreMemorization(data);
                }
            }, 1500);
        }
    }

    @Override
    public void onGameOver() {
        NumberFlashBus.gameState = GameState.RESULTS;
    }

    @Override
    public void onShutdown() {}

    @Override
    public void onRestoreInstanceState(Bundle inState) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState = data.saveToBundle(outState);
    }
}
