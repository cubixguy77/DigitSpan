package com.mastersofmemory.flashnumbers.keyboard;

import com.mastersofmemory.flashnumbers.GameData;

public interface Keyboard {

    interface View {
        void enable();
        void disable();
    }

    interface Presenter {
        void onKeyPress(char digit);
        void onBackspacePress();
        void setData(GameData data);
    }

}
