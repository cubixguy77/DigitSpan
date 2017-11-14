package com.mastersofmemory.flashnumbers.settings;

public class DigitSpeedSetting {
    private DigitSpeed digitSpeed;
    private int millisToDisplayDigit;
    private int millisBetweenDigits;

    public DigitSpeedSetting(DigitSpeed digitSpeed) {
        this.digitSpeed = digitSpeed;

        if (digitSpeed == DigitSpeed.SLOW) {
            this.millisToDisplayDigit = 1500;
            this.millisBetweenDigits = 1000;
        }
        else if (digitSpeed == DigitSpeed.MEDIUM) {
            this.millisToDisplayDigit = 750;
            this.millisBetweenDigits = 500;
        }
        else if (digitSpeed == DigitSpeed.FAST) {
            this.millisToDisplayDigit = 500;
            this.millisBetweenDigits = 200;
        }
    }

    public int getMillisToDisplayDigit() {
        return millisToDisplayDigit;
    }

    public int getMillisBetweenDigits() {
        return millisBetweenDigits;
    }

    String getStorageCode() {
        return digitSpeed.getCode();
    }
}
