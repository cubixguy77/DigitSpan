package com.mastersofmemory.flashnumbers;

public class FlashSettingUtil {

    private static final int NUM_DIGITS_INDEX = 0;
    private static final int DIGIT_SPEED_INDEX = 1;

    public static Setting getNumDigitsSetting(Challenge challenge) {
        return getSetting(challenge, NUM_DIGITS_INDEX);
    }

    public static Setting getDigitSpeedSetting(Challenge challenge) {
        return getSetting(challenge, DIGIT_SPEED_INDEX);
    }

    private static Setting getSetting(Challenge challenge, int index) {
        if (challenge.getSettings() == null || challenge.getSettings().size() <= index)
            return null;
        return challenge.getSettings().get(index);
    }
}
