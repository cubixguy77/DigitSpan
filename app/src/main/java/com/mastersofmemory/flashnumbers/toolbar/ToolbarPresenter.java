package com.mastersofmemory.flashnumbers.toolbar;

import com.mastersofmemory.flashnumbers.GameData;
import com.mastersofmemory.flashnumbers.NumberFlashBus;
import com.mastersofmemory.flashnumbers.NumberFlashGameStateListener;
import com.mastersofmemory.flashnumbers.NumberFlashResult;

public class ToolbarPresenter implements NumberFlashGameStateListener, LifeListener {

    private final Toolbar.View toolbar;

    ToolbarPresenter(Toolbar.View toolbar) {
        this.toolbar = toolbar;
        NumberFlashBus.getBus().subscribe(this);
    }

    @Override
    public void onLifeLost(int livesRemaining) {
        toolbar.displayLivesRemaining(livesRemaining);
    }

    @Override
    public void onPreMemorization(GameData data) {
        toolbar.displayLivesRemaining(data.getNumLivesRemaining());
    }

    @Override
    public void onMemorizationStart() {}

    @Override
    public void onRecallStart() {}

    @Override
    public void onRecallComplete(NumberFlashResult result) {}

    @Override
    public void onGameOver() {}

    @Override
    public void onShutdown() {}
}
