package com.mastersofmemory.flashnumbers.settings;

public class NumberFlashConfig {

    private DigitSpeedSetting digitSpeed;
    private int numInitialDigits;

    public NumberFlashConfig(Settings settings) {
        this.numInitialDigits = settings.getInitialNumberOfDigits();
        this.digitSpeed = settings.getDigitSpeedSetting();
    }

    public int getNumInitialDigits() {
        return this.numInitialDigits;
    }

    public DigitSpeedSetting getDigitSpeed() {
        return digitSpeed;
    }
}
