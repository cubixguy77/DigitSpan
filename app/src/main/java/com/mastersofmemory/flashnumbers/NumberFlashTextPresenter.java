package com.mastersofmemory.flashnumbers;

import android.os.Bundle;
import android.os.Handler;

class NumberFlashTextPresenter implements NumberFlashGameStateListener, RecallDataChangeListener {

    private NumberFlashTextView view;
    private GameData data;

    NumberFlashTextPresenter(NumberFlashTextView view) {
        this.view = view;
        NumberFlashBus.getBus().subscribe(this);
    }

    public void setData(GameData data) {
        this.data = data;
    }

    @Override
    public void onLoad(NumberFlashConfig config, Bundle savedInstanceState) {
        this.view.showPreMemorizationState();
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
                    NumberFlashBus.getBus().onTransitionToRecall();
                    return;
                }

                view.displayDigit(Character.getNumericValue(data.getMemoryDigit(digitNum++)));
                handler.postDelayed(this, data.getConfig().getDigitSpeed().getMillisToDisplayDigit());
            }
        }, 0);
    }

    @Override
    public void onTransitionToRecall() {
        view.showRecallState();
    }

    @Override
    public void onRecallComplete(NumberFlashResult result) {
        view.displayAnswerResponse(result.isCorrect());
    }

    @Override
    public void onPlayAgain(NumberFlashConfig config) {}

    @Override
    public void onGameOver() {
        view.setText("");
    }

    @Override
    public void onShutdown() {
        view = null;
    }

    @Override
    public void onRecallDataChanged(char[] recallData) {
        view.refreshRecallData(recallData);
    }
}