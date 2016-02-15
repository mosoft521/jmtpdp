package com.gmail.mosoft521.jmtpdp.ch01;

public class JavaThreadCreationAndRun {

    public static void main(String[] args) {
        System.out.println("The main method was executed by thread:" + Thread.currentThread().getName());
        Helper helper = new Helper("Java Thread AnyWhere");

        //创建一个线程
        Thread thread = new Thread(helper);

        //设置线程名
        thread.setName("A-Worker-Thread");

        //启动线程
        thread.start();
    }

    static class Helper implements Runnable {
        private final String message;

        public Helper(String message) {
            this.message = message;
        }

        private void doSomething(String message) {
            System.out.println("The doSomething method was executed by thread:" + Thread.currentThread().getName());

            System.out.println("Do something with " + message);
        }

        @Override
        public void run() {
            doSomething(message);
        }
    }//end of Helper
}
/*
The main method was executed by thread:main
The doSomething method was executed by thread:A-Worker-Thread
Do something with Java Thread AnyWhere
 */