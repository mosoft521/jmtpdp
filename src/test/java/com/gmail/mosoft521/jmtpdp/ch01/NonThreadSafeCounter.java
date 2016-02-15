package com.gmail.mosoft521.jmtpdp.ch01;

/**
 * 非线程安全的计数器。
 *
 * @author Viscent Huang
 */
public class NonThreadSafeCounter {
    private int counter = 0;

    public void increment() {
        counter++;
    }

    public int get() {
        return counter;
    }
}
