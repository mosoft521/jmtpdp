package com.gmail.mosoft521.jmtpdp.ch12ms.example.testdatagen;

import com.gmail.mosoft521.jmtpdp.ch05tpt.AbstractTerminatableThread;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 生成本章实战案例运行时所需的接口日志文件。
 *
 * @author Viscent Huang
 */
public class TestingDataGen {

    public static void main(String[] args) {

        final long duration = 5 * 60; // 单位：s

        // 模拟接口日志文件中的请求速率
        final int tps = 100;
        final AbstractTerminatableThread att = new AbstractTerminatableThread() {
            final RequestFactory[] factories = new RequestFactory[]{
                    new SmsRequestFactory(), new ChargingRequestFactory(),
                    new LocationRequestFactory()};

            Logger logger = new Logger();
            int count = 0;

            @Override
            protected void doRun() throws Exception {
                RequestFactory rf;
                SimulatedRequest req;
                Random rnd = new Random();
                int i = 0;

                i = rnd.nextInt(factories.length);
                rf = factories[i];

                req = rf.newRequest();

                req.printLogs(logger);

                count++;

                if (0 == (count % tps)) {
                    System.out.println(count);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count = 0;
                }

            }

            @Override
            protected void doCleanup(Exception cause) {
                System.out.println("count:" + count + ",rate:" + (count / duration));
            }

        };

        att.setDaemon(false);

        att.start();

        final Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                att.terminate();
            }
        }, duration * 1000);
    }
}