package com.mastersofmemory.flashnumbers.digitviewer;

import android.os.Handler;

import com.mastersofmemory.flashnumbers.gamestate.GameData;
import com.mastersofmemory.flashnumbers.NumberFlashBus;
import com.mastersofmemory.flashnumbers.gamestate.NumberFlashGameStateListener;
import com.mastersofmemory.flashnumbers.gamestate.NumberFlashResult;

public class DigitViewerPresenter implements DigitViewer.Presenter, NumberFlashGameStateListener, RecallDataChangeListener {

    private DigitViewer.View view;
    private GameData data;

    DigitViewerPresenter(DigitViewer.View view) {
        this.view = view;
        view.setPresenter(this);
        NumberFlashBus.getBus().subscribe(this);
    }

    @Override
    public void onPreMemorization(GameData data) {
        this.data = data;
        this.view.showPreMemorizationState(data.getNumDigitsToAttempt());
    }

    @Override
    public void onMemorizationStart() {
        view.showMemorizationState();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            int digitNum = 0;

            @Override
            public void run()
            {
                if (digitNum >= data.getNumDigitsToAttempt()) {
                    NumberFlashBus.getBus().onRecallStart();
                    return;
                }

                view.displayDigit(Character.getNumericValue(data.getMemoryDigit(digitNum++)));
                handler.postDelayed(this, data.getConfig().getDigitSpeed().getMillisToDisplayDigit());
            }
        }, 0);
    }

    @Override
    public void onRecallStart() {
        view.showRecallState();
    }

    @Override
    public void onRecallComplete(NumberFlashResult result) {
        view.displayAnswerResponse(result.isCorrect());

        if (result.isCorrect() && result.getNumDigitsAttempted() > data.getNumDigitsAchieved()) {
            data.registerDigitsAchieved(result.getNumDigitsAttempted());
        }
    }

    @Override
    public void onGameOver() {
        view.showGameOverState(data.getNumDigitsAchieved());
    }

    @Override
    public void onShutdown() {
        view = null;
    }

    @Override
    public void onRecallDataChanged(char[] recallData) {
        view.refreshRecallData(recallData);
    }

    @Override
    public void onStartClick() {
        view.showCountdown();
    }

    @Override
    public void onResetClick() {
        data.resetTo(data.getConfig().getNumInitialDigits());
        data.resetLives();
        NumberFlashBus.getBus().onPreMemorization(data);
    }

    @Override
    public void onCountdownComplete() {
        NumberFlashBus.getBus().onMemorizationStart();
    }
}