package com.mastersofmemory.flashnumbers;


public class NumberFlashConfig {

    private int numInitialDigits;

    public NumberFlashConfig(int numInitialDigits) {
        this.numInitialDigits = numInitialDigits;
    }

    public int getNumInitialDigits() {
        return this.numInitialDigits;
    }
}
