package com.mastersofmemory.flashnumbers.gamestate;

import android.os.Bundle;

import com.mastersofmemory.flashnumbers.datageneration.MemoryDataSetFactory;
import com.mastersofmemory.flashnumbers.NumberFlashBus;
import com.mastersofmemory.flashnumbers.settings.NumberFlashConfig;

public class GameData {

    private NumberFlashConfig config;
    private char[] memoryDigits;
    private char[] recallDigits;
    private int numDigitsRecalled;
    private int numDigitsAchieved;
    private int numLivesRemaining;
    private int numDigitsAttempted;

    public GameData(NumberFlashConfig config, Bundle bundle) {
        this.config = config;
        this.numDigitsRecalled = 0;
        this.numDigitsAchieved = bundle == null ? 0 : bundle.getInt("GameData.numDigitsAchieved", 0);
        this.numLivesRemaining = bundle == null ? 3 : bundle.getInt("GameData.numLivesRemaining", 3);
        this.numDigitsAttempted = bundle == null ? config.getNumInitialDigits() : bundle.getInt("GameData.numDigitsAttempted", config.getNumInitialDigits());
        initializeMemoryRecallData(numDigitsAttempted);
    }

    Bundle saveToBundle(Bundle bundle) {
        bundle.putCharArray("GameData.memoryDigits", memoryDigits);
        bundle.putCharArray("GameData.recallDigits", recallDigits);
        bundle.putInt("GameData.numDigitsRecalled", numDigitsRecalled);
        bundle.putInt("GameData.numDigitsAchieved", numDigitsAchieved);
        bundle.putInt("GameData.numLivesRemaining", numLivesRemaining);
        bundle.putInt("GameData.numDigitsAttempted", numDigitsAttempted);
        return bundle;
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
        int numLivesBefore = this.numLivesRemaining;
        this.numLivesRemaining = 3;
        NumberFlashBus.getBus().onLivesUpdated(numLivesBefore, this.numLivesRemaining);
    }

    void loseLife() {
        NumberFlashBus.getBus().onLivesUpdated(this.numLivesRemaining, --this.numLivesRemaining);
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
