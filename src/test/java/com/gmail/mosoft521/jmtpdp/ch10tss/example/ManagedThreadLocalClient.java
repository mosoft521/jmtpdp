package com.gmail.mosoft521.jmtpdp.ch10tss.example;


import com.gmail.mosoft521.jmtpdp.ch10tss.ManagedThreadLocal;

public class ManagedThreadLocalClient {
    private final static ManagedThreadLocal<Integer> mtl = ManagedThreadLocal
            .newInstance(new ManagedThreadLocal.InitialValueProvider<Integer>() {

                @Override
                protected Integer initialValue() {
                    System.out.println(Thread.currentThread().getName());
                    return Integer.valueOf((int) Thread.currentThread().getId());
                }

            });

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Helper().start();
        }
    }

    static class Helper extends Thread {

        @Override
        public void run() {

            System.out.println(mtl.get());
        }
    }
}
/*
Thread-1
12
Thread-2
13
Thread-0
11
 */