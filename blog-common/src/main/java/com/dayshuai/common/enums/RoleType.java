package com.dayshuai.common.enums;

public enum RoleType {

    ADMIN("0"),
    USER("1");

    private final String value;
    RoleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
