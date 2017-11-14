package com.mastersofmemory.flashnumbers;

public class NumberFlashResult {

    private boolean isCorrect;
    private final int numDigitsAttempted;

    public NumberFlashResult(char[] memory, char[] recall) {
        this.isCorrect = verifyResult(memory, recall);
        this.numDigitsAttempted = memory.length;
    }

    boolean isCorrect() {
        return this.isCorrect;
    }

    private boolean verifyResult(char[] memory, char[] recall) {
        for (int i=0; i<memory.length; i++) {
            if (memory[i] != recall[i])
                return false;
        }

        return true;
    }

    public int getNumDigitsAttempted() {
        return numDigitsAttempted;
    }
}
