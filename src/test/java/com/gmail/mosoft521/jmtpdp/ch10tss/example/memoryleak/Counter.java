package com.gmail.mosoft521.jmtpdp.ch10tss.example.memoryleak;

public class Counter {
    private int i = 0;

    public int getAndIncrement() {
        return (i++);
    }
}