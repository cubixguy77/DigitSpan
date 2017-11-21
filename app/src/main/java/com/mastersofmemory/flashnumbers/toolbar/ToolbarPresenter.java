package com.mastersofmemory.flashnumbers.toolbar;

import com.mastersofmemory.flashnumbers.gamestate.GameData;
import com.mastersofmemory.flashnumbers.NumberFlashBus;
import com.mastersofmemory.flashnumbers.gamestate.NumberFlashGameStateListener;
import com.mastersofmemory.flashnumbers.gamestate.NumberFlashResult;

public class ToolbarPresenter implements NumberFlashGameStateListener, LifeListener {

    private final Toolbar.View toolbar;

    ToolbarPresenter(Toolbar.View toolbar) {
        this.toolbar = toolbar;
        NumberFlashBus.getBus().subscribe(this);
    }

    @Override
    public void onLivesUpdated(int numLivesBefore, int numLivesAfter) {
        toolbar.displayLivesRemaining(numLivesAfter);
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
