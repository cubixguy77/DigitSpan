package com.mastersofmemory.flashnumbers;

public interface NumberFlashGameStateListener {

    void onPreMemorization(GameData data);
    void onMemorizationStart();
    void onRecallStart();
    void onRecallComplete(NumberFlashResult result);
    void onGameOver();
    void onShutdown();

}
