package com.mastersofmemory.flashnumbers.settings;

import java.util.HashMap;
import java.util.Map;

public enum DigitSpeed {
    SLOW("SLOW("),
    MEDIUM("MEDIUM"),
    FAST("FAST");

    private final String code;
    private static final Map<String,DigitSpeed> valuesByCode;

    static {
        valuesByCode = new HashMap<>(values().length);
        for(DigitSpeed value : values()) {
            valuesByCode.put(value.code, value);
        }
    }

    DigitSpeed(String code) {
        this.code = code;
    }

    public static DigitSpeed lookupByCode(String code) {
        return valuesByCode.get(code);
    }

    public String getCode() {
        return code;
    }
}