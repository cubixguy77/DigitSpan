package com.mastersofmemory.flashnumbers;

import com.mastersofmemory.flashnumbers.settings.DigitSpeed;
import com.mastersofmemory.flashnumbers.settings.DigitSpeedSetting;
import com.mastersofmemory.flashnumbers.settings.Settings;

public class NumberFlashConfig {

    private DigitSpeedSetting digitSpeed;
    private int numInitialDigits;

    NumberFlashConfig(int numInitialDigits) {
        this.numInitialDigits = numInitialDigits;
        this.digitSpeed = new DigitSpeedSetting(DigitSpeed.MEDIUM);
    }

    NumberFlashConfig(Settings settings) {
        this.numInitialDigits = settings.getInitialNumberOfDigits();
        this.digitSpeed = settings.getDigitSpeedSetting();
    }

    int getNumInitialDigits() {
        return this.numInitialDigits;
    }

    DigitSpeedSetting getDigitSpeed() {
        return digitSpeed;
    }
}
