package com.mastersofmemory.flashnumbers;

import android.os.Bundle;

public interface NumberFlashGameStateListener extends GameStateListener {

    void onLoad(NumberFlashConfig config, Bundle savedInstanceState);
    void onRecallComplete(NumberFlashResult result);
    void onPlayAgain(NumberFlashConfig config);
    void onGameOver();

}
