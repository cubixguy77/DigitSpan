package com.mastersofmemory.flashnumbers.keyboard;

import android.os.Bundle;
import android.os.Handler;

import com.mastersofmemory.flashnumbers.GameData;
import com.mastersofmemory.flashnumbers.GameState;
import com.mastersofmemory.flashnumbers.NumberFlashBus;
import com.mastersofmemory.flashnumbers.NumberFlashConfig;
import com.mastersofmemory.flashnumbers.NumberFlashGameStateListener;
import com.mastersofmemory.flashnumbers.NumberFlashResult;

public class KeyboardPresenter implements Keyboard.Presenter, NumberFlashGameStateListener {

    private GameData data;
    private Keyboard.View keyboard;

    public KeyboardPresenter(Keyboard.View keyboard) {
        this.keyboard = keyboard;
        NumberFlashBus.getBus().subscribe(this);
    }

    public void setData(GameData data) {
        this.data = data;
    }

    @Override
    public void onKeyPress(char digit) {
        if (data.getNumDigitsRecalled() >= data.getNumDigitsToAttempt()) // User entering keys furiously
            return;

        data.recallDigit(digit);

        if (data.getNumDigitsRecalled() >= data.getNumDigitsToAttempt()) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    NumberFlashBus.getBus().onRecallComplete(new NumberFlashResult(data.getMemoryDigits(), data.getRecallDigits()));
                }
            }, 300);
        }
    }

    @Override
    public void onBackspacePress() {
        if (data.getNumDigitsRecalled() > 0) {
            data.eraseLastDigit();
        }
    }


    @Override
    public void onMemorizationStart() {}

    @Override
    public void onTransitionToRecall() {
        keyboard.enable();
    }

    @Override
    public void onShutdown() {
        keyboard = null;
    }

    @Override
    public void onLoad(NumberFlashConfig config, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (NumberFlashBus.gameState == GameState.RECALL) {
                keyboard.enable();
            } else {
                keyboard.disable();
            }
        }
        else {
            keyboard.disable();
        }
    }

    @Override
    public void onRecallComplete(NumberFlashResult result) {
        keyboard.disable();
    }

    @Override
    public void onPlayAgain(NumberFlashConfig config) {}

    @Override
    public void onGameOver() {}
}
