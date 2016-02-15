package com.gmail.mosoft521.jmtpdp.ch05tpt.example;

public enum AlarmType {
    FAULT("fault"),
    RESUME("resume");

    private final String name;

    private AlarmType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}