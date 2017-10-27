package com.mastersofmemory.flashnumbers;

import android.content.res.Resources;
import android.support.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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

    static char[] getPiData(int numDigits) {
        char[] data;

        if (numDigits <= 100)
            data = getStringFromRawResourse(R.raw.pi_100, numDigits).toCharArray();
        else if (numDigits <= 1000)
            data = getStringFromRawResourse(R.raw.pi_1000, numDigits).toCharArray();
        else if (numDigits <= 10000)
            data = getStringFromRawResourse(R.raw.pi_10000, numDigits).toCharArray();
        else
            data = getStringFromRawResourse(R.raw.pi_100000, numDigits).toCharArray();

        return data;
    }

    @Nullable
    private static String getStringFromRawResourse(int rawRes, int numCharsToTake) {

        InputStream inputStream;
        try {
            inputStream = MyApplication.getAppContext().getResources().openRawResource(rawRes);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                inputStream.close();
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String resultString;
        try {
            resultString = byteArrayOutputStream.toString("UTF-8").substring(0, numCharsToTake);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        return resultString;
    }
}
