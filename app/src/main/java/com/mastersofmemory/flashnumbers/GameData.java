package com.mastersofmemory.flashnumbers;

public class GameData {

    private NumberFlashConfig config;
    private char[] memoryDigits;
    private char[] recallDigits;
    private int numDigitsRecalled;
    private int numDigitsAchieved;
    private int numLivesRemaining;
    private int numDigitsAttempted;

    GameData(NumberFlashConfig config) {
        this.config = config;
        this.numDigitsRecalled = 0;
        this.numDigitsAchieved = 0;
        this.numLivesRemaining = 3;
        this.numDigitsAttempted = config.getNumInitialDigits();
        initializeMemoryRecallData(numDigitsAttempted);
    }

    private void initializeMemoryRecallData(int numDigits) {
        this.memoryDigits = MemoryDataSetFactory.getDecimalNumberData(numDigits, true);
        this.recallDigits = new char[numDigits];
    }

    public int getNumDigitsRecalled() {
        return this.numDigitsRecalled;
    }

    public int getNumDigitsToAttempt() {
        return this.numDigitsAttempted;
    }

    public void recallDigit(char digit) {
        recallDigits[this.numDigitsRecalled++] = digit;
        notifyRecallDataChanged();
    }

    public void eraseLastDigit() {
        recallDigits[--numDigitsRecalled] = '\u0000';
        notifyRecallDataChanged();
    }

    private void notifyRecallDataChanged() {
        NumberFlashBus.getBus().onRecallDataChanged(this.recallDigits);
    }

    public char[] getMemoryDigits() {
        return memoryDigits;
    }

    public char getMemoryDigit(int index) {
        return memoryDigits[index];
    }

    public char[] getRecallDigits() {
        return recallDigits;
    }

    public NumberFlashConfig getConfig() {
        return config;
    }

    public void registerDigitsAchieved(int numDigitsAchieved) {
        if (numDigitsAchieved > this.numDigitsAchieved) {
            this.numDigitsAchieved = numDigitsAchieved;
        }
    }

    public int getNumDigitsAchieved() {
        return numDigitsAchieved;
    }

    public void resetLives() {
        this.numLivesRemaining = 3;
    }

    void loseLife() {
        NumberFlashBus.getBus().onLifeLost(--this.numLivesRemaining);
    }

    public int getNumLivesRemaining() {
        return this.numLivesRemaining;
    }

    public void resetTo(int numDigitsToAttempt) {
        this.numDigitsAttempted = numDigitsToAttempt;
        this.numDigitsRecalled = 0;
        this.initializeMemoryRecallData(numDigitsAttempted);
    }
}
