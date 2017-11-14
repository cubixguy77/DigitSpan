package com.mastersofmemory.flashnumbers;

public class GameData {

    private NumberFlashConfig config;
    private char[] memoryDigits;
    private char[] recallDigits;
    private int numDigitsRecalled;

    GameData(NumberFlashConfig config) {
        this.config = config;
        this.memoryDigits = MemoryDataSetFactory.getDecimalNumberData(config.getNumInitialDigits(), true);
        this.recallDigits = new char[config.getNumInitialDigits()];
        this.numDigitsRecalled = 0;
    }

    public int getNumDigitsRecalled() {
        return this.numDigitsRecalled;
    }

    public int getNumDigitsToAttempt() {
        return this.config.getNumInitialDigits();
    }

    public void recallDigit(char digit) {
        recallDigits[this.numDigitsRecalled++] = digit;
        notifyRecallDataChanged();
    }

    public void eraseLastDigit() {
        recallDigits[this.numDigitsRecalled--] = '\u0000';
        notifyRecallDataChanged();
    }

    private void notifyRecallDataChanged() {
        NumberFlashBus.getBus().onRecallDataChanged(this.recallDigits);
    }

    public char[] getMemoryDigits() {
        return memoryDigits;
    }

    char getMemoryDigit(int index) {
        return memoryDigits[index];
    }

    public char[] getRecallDigits() {
        return recallDigits;
    }

    public NumberFlashConfig getConfig() {
        return config;
    }
}
