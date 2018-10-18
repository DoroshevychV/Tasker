package com.tasker.dto.response;

public class TokenModel {

    private String key;

    private String value;

    public TokenModel() {
    }

    public TokenModel(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TokenModel{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
