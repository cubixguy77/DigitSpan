package com.mastersofmemory.flashnumbers;

import android.os.Bundle;
import android.util.Log;

import com.mastersofmemory.flashnumbers.digitviewer.RecallDataChangeListener;
import com.mastersofmemory.flashnumbers.gamestate.GameData;
import com.mastersofmemory.flashnumbers.gamestate.GameState;
import com.mastersofmemory.flashnumbers.gamestate.NumberFlashGameStateListener;
import com.mastersofmemory.flashnumbers.gamestate.NumberFlashResult;
import com.mastersofmemory.flashnumbers.gamestate.SaveInstanceStateListener;
import com.mastersofmemory.flashnumbers.toolbar.LifeListener;

import java.util.ArrayList;

public class NumberFlashBus implements NumberFlashGameStateListener, SaveInstanceStateListener, RecallDataChangeListener, LifeListener {

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

    static void unsubscribeAll() {
        Log.d("ML.NumberFlashBus", "unsubscribeAll()");
        if (NumberFlashBus.instance != null) {
            NumberFlashBus.instance.observers.clear();
            NumberFlashBus.instance = null;
        }
    }

    @Override
    public void onPreMemorization(GameData data) {
        for (Object observer : observers) {
            if (observer != null && observer instanceof NumberFlashGameStateListener) {
                ((NumberFlashGameStateListener) observer).onPreMemorization(data);
            }
        }
    }

    @Override
    public void onMemorizationStart() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof NumberFlashGameStateListener) {
                ((NumberFlashGameStateListener) observer).onMemorizationStart();
            }
        }
    }

    @Override
    public void onRecallStart() {
        for (Object observer : observers) {
            if (observer != null && observer instanceof NumberFlashGameStateListener) {
                ((NumberFlashGameStateListener) observer).onRecallStart();
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
    public void onGameOver() {
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

    @Override
    public void onLivesUpdated(int numLivesBefore, int numLivesAfter) {
        for (Object observer : observers) {
            if (observer != null && observer instanceof LifeListener) {
                ((LifeListener) observer).onLivesUpdated(numLivesBefore, numLivesAfter);
            }
        }
    }
}
