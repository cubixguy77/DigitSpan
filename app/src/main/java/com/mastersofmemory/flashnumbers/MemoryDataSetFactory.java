package com.mastersofmemory.flashnumbers;

import java.util.Random;

class MemoryDataSetFactory {

    static char[] getDecimalNumberData(int numDigits, boolean eliminateRepeats) {
        char[] data = new char[numDigits];
        Random rand = new Random();

        for (int i=0; i<numDigits; i++) {
            data[i] = Character.forDigit(rand.nextInt(10), 10);
            while (eliminateRepeats && i > 0 && data[i] == data[i-1]) {
                data[i] = Character.forDigit(rand.nextInt(10), 10);
            }
        }

        return data;
    }
}
