package com.mastersofmemory.flashnumbers;

public interface FlashNumbers {

    interface Keyboard {
        interface UserKeyboardActions {
            /* Number Keypad Buttons */
            void onKeyPress(char digit);
            void onBackSpace();
        }
    }
}
