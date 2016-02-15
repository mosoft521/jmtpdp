package com.gmail.mosoft521.jmtpdp.ch01;

/**
 * 线程安全的计数器。
 *
 * @author Viscent Huang
 */
public class ThreadSafeCounter {
    private int counter = 0;

    public void increment() {
        synchronized (this) {
            counter++;
        }
    }

    public int get() {
        synchronized (this) {
            return counter;
        }
    }
}