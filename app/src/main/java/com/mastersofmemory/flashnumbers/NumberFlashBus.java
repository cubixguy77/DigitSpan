package com.mastersofmemory.flashnumbers;

import android.os.Bundle;

import com.mastersofmemory.flashnumbers.settings.Settings;

import java.util.ArrayList;

public class NumberFlashBus implements NumberFlashGameStateListener, SaveInstanceStateListener, RecallDataChangeListener {

    private static NumberFlashBus instance = null;
    private ArrayList<Object> observers;

    public static GameState gameState;

    private NumberFlashBus() {
        observers = new ArrayList<>();
    }

    /* Returns an instance of NumberFlashBus
     * NumberFlashBus is a Singleton and an instance will be created the first time this method is called
     * The instance will be destroyed on device rotation or on play again, but the static fields will retain their values
     */
    public static NumberFlashBus getBus() {
        if(NumberFlashBus.instance == null) {
            NumberFlashBus.instance = new NumberFlashBus();
        }
        return NumberFlashBus.instance;
    }

    public void subscribe(Object observer) {
        if (observer != null && !observers.contains(observer)) {
            this.observers.add(observer);
        }
    }

    boolean hasObservers() {
        return !observers.isEmpty();
    }

    static void unsubscribeAll() {
        System.out.println("NumberFlashBus.unSubscribeAll()");
        if (NumberFlashBus.instance != null) {
            NumberFlashBus.instance.observers.clear();
            NumberFlashBus.instance = null;
        }
    }

    static void destroy() {
    }
    
    @Override
    public void onLoad(NumberFlashConfig config, Bundle savedInstanceState) {
        gameState = GameState.PRE_MEMORIZATION;

        for (Object observer : observers) {
            if (observer != null && observer instanceof NumberFlashGameStateListener) {
                ((NumberFlashGameStateListener) observer).onLoad(config, savedInstanceState);
            }
        }
    }

    @Override
    public void onMemorizationStart() {
        gameState = GameState.MEMORIZATION;

        for (Object observer : observers) {
            if (observer != null && observer instanceof NumberFlashGameStateListener) {
                ((NumberFlashGameStateListener) observer).onMemorizationStart();
            }
        }
    }

    @Override
    public void onTransitionToRecall() {
        gameState = GameState.RECALL;

        for (Object observer : observers) {
            if (observer != null && observer instanceof NumberFlashGameStateListener) {
                ((NumberFlashGameStateListener) observer).onTransitionToRecall();
            }
        }
    }

    @Override
    public void onRecallComplete(NumberFlashResult result) {
        for (Object observer : observers) {
            if (observer != null && observer instanceof NumberFlashGameStateListener) {
                ((NumberFlashGameStateListener) observer).onRecallComplete(result);
            }
        }
    }

    @Override
    public void onPlayAgain(NumberFlashConfig config) {
        for (Object observer : observers) {
            if (observer != null && observer instanceof NumberFlashGameStateListener) {
                ((NumberFlashGameStateListener) observer).onPlayAgain(config);
            }
        }
    }

    @Override
    public void onGameOver() {
        gameState = GameState.REVIEW;
        for (Object observer : observers) {
            if (observer != null && observer instanceof NumberFlashGameStateListener) {
                ((NumberFlashGameStateListener) observer).onGameOver();
            }
        }
    }

    @Override
    public void onShutdown() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof NumberFlashGameStateListener) {
                ((NumberFlashGameStateListener) observer).onShutdown();
            }
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle inState) {
        for (Object observer : observers) {
            if (observer != null && observer instanceof SaveInstanceStateListener) {
                ((SaveInstanceStateListener) observer).onRestoreInstanceState(inState);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        for (Object observer : observers) {
            if (observer != null && observer instanceof SaveInstanceStateListener) {
                ((SaveInstanceStateListener) observer).onSaveInstanceState(outState);
            }
        }
    }

    @Override
    public void onRecallDataChanged(char[] recallData) {
        for (Object observer : observers) {
            if (observer != null && observer instanceof RecallDataChangeListener) {
                ((RecallDataChangeListener) observer).onRecallDataChanged(recallData);
            }
        }
    }
}
