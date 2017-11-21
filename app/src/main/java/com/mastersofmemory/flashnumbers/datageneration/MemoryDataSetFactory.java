package com.mastersofmemory.flashnumbers.datageneration;

import java.util.Random;

public class MemoryDataSetFactory {

    public static char[] getDecimalNumberData(int numDigits, boolean eliminateRepeats) {
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
