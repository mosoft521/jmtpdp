package com.gmail.mosoft521.jmtpdp.terminology;

public class ClientThread {

    public static void main(String[] args) {

        ThreadA t = new ThreadA();
        t.start();
    }

    private static class ThreadA extends Thread {

        @Override
        public void run() {
            Helper helper = new Helper();

            // 此时hello方法的客户端线程就是ThreadA线程。
            String msg = helper.hello("helper");
            System.out.println(msg);
        }

    }

    private static class Helper {

        public String hello(String msg) {
            return "Hi," + msg;
        }
    }
}
/*
Hi,helper
 */