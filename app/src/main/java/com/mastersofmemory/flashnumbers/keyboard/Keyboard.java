package com.mastersofmemory.flashnumbers.keyboard;

public interface Keyboard {

    interface View {
        void enable();
        void disable();
    }

    interface Presenter {
        void onKeyPress(char digit);
        void onBackspacePress();
    }

}
