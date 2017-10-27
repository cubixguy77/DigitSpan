package com.mastersofmemory.flashnumbers;

public class Setting {
    private long challengeKey;
    private long settingKey;
    private int value;
    private String settingName;
    private int sortOrder;
    private boolean visible;

    public Setting(long challengeKey, long settingKey, int value, String settingName, int sortOrder, boolean visible) {
        this.challengeKey = challengeKey;
        this.settingKey = settingKey;
        this.value = value;
        this.settingName = settingName;
        this.sortOrder = sortOrder;
        this.visible = visible;
    }

    public long getChallengeKey() {
        return challengeKey;
    }

    public void setSettingKey(long settingKey) {
        this.settingKey = settingKey;
    }

    public long getSettingKey() {
        return settingKey;
    }

    public void setValue(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

    public String getSettingName() {
        return settingName;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setChallengeKey(long challengeKey) {
        this.challengeKey = challengeKey;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("----- Setting -----"); sb.append(System.getProperty("line.separator"));
        sb.append("Name: " + settingName); sb.append(System.getProperty("line.separator"));
        sb.append("Challenge Key: " + challengeKey); sb.append(System.getProperty("line.separator"));
        sb.append("Setting Key: " + settingKey); sb.append(System.getProperty("line.separator"));
        sb.append("Sort Order: " + sortOrder); sb.append(System.getProperty("line.separator"));
        sb.append("Visible: " + (visible ? "True" : "False")); sb.append(System.getProperty("line.separator"));
        sb.append("Value: " + value); sb.append(System.getProperty("line.separator"));
        sb.append("----- End Setting -----"); sb.append(System.getProperty("line.separator"));
        return sb.toString();
    }
}
