package com.mastersofmemory.flashnumbers;

public interface GameStateListener {

    void onMemorizationStart();
    void onTransitionToRecall();
    void onShutdown();

}
