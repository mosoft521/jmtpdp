package com.gmail.mosoft521.jmtpdp.ch10tss.example;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SmsVerficationCodeSender {
    private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(1,
            Runtime.getRuntime().availableProcessors(), 60, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "VerfCodeSender");
            t.setDaemon(true);
            return t;
        }

    }, new ThreadPoolExecutor.DiscardPolicy());

    public static void main(String[] args) {
        SmsVerficationCodeSender client = new SmsVerficationCodeSender();

        client.sendVerificationSms("18912345678");
        client.sendVerificationSms("18712345679");
        client.sendVerificationSms("18612345676");

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            ;
        }
    }

    /**
     * 生成并下发验证码短信到指定的手机号码。
     *
     * @param msisdn 短信接收方号码。
     */
    public void sendVerificationSms(final String msisdn) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                //生成强随机数验证码
                int verificationCode = ThreadSpecificSecureRandom.INSTANCE
                        .nextInt(999999);
                DecimalFormat df = new DecimalFormat("000000");
                String txtVerCode = df.format(verificationCode);

                //发送验证码短信
                sendSms(msisdn, txtVerCode);
            }

        };

        EXECUTOR.submit(task);

    }

    private void sendSms(String msisdn, String verificationCode) {
        System.out.println("Sending verification code " + verificationCode + " to "
                + msisdn);
        // 省略其它代码
    }
}
/*
todo:空白输出?
 */