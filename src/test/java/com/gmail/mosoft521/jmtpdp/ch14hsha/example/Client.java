package com.gmail.mosoft521.jmtpdp.ch14hsha.example;

import com.gmail.mosoft521.jmtpdp.ch05tpt.example.AlarmType;
import com.gmail.mosoft521.jmtpdp.util.Debug;
import org.apache.log4j.Logger;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Client {
    private final static Logger logger = Logger.getLogger(Client.class);

    public static void main(String[] args) {
//		Debug.info("started.");
//		
//		final int supressThredshold=10;
//		final AlarmMgr alarmMgr=AlarmMgr.getInstance();
//		alarmMgr.init();
//		
//		final String alarmId="10000020";
//		final String alarmExtraInfo= "name1=test;name2=value2";
//		
//		Thread thread;
//		
//		thread=new Thread(){
//			
//			@Override
//			public void run(){
//				for(int i=0;i<supressThredshold;i++){
//					alarmMgr.sendAlarm(AlarmType.FAULT,alarmId ,alarmExtraInfo);
//				}
//				
//				alarmMgr.disconnect();
//				
//				int duplicateSubmissionCount;
//				duplicateSubmissionCount=alarmMgr.sendAlarm(AlarmType.FAULT,alarmId,alarmExtraInfo);
//				if(duplicateSubmissionCount<supressThredshold){
//					logger.error("Alarm["+alarmId+"] raised,extraInfo:"+alarmExtraInfo);
//				}else{
//					if(duplicateSubmissionCount==supressThredshold){
//						logger.error("Alarm["+alarmId+"] was raised more than "+supressThredshold+" times, it will no longer be logged,extraInfo:"+alarmExtraInfo);
//					}
//				}
//			}
//		};
//		
//		thread.start();
//			
//		
//		
//		//Resume
//		try {
//	    thread.join();
//    } catch (InterruptedException e1) {
//    	;
//    }
//		alarmMgr.sendAlarm(AlarmType.RESUME,alarmId ,alarmExtraInfo);
//		
//		//Fault re-occur
//		try {
//	    Thread.sleep(500);
//    } catch (InterruptedException e) {
//    	;
//    }
//		
//		alarmMgr.sendAlarm(AlarmType.FAULT,alarmId ,alarmExtraInfo);
//		alarmMgr.shutdown();
        test();
    }

    private static void test() {
        Debug.info("started.");

        final AlarmMgr alarmMgr = AlarmMgr.getInstance();
        alarmMgr.init();

        final String alarmId = "10000020";
        final String alarmExtraInfo = "name1=test;name2=value2";

        Thread t1;


        int count1 = 10;
        final Timer timer = new Timer(true);
        final CyclicBarrier cbr = new CyclicBarrier(count1, new Runnable() {

            @Override
            public void run() {

                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        alarmMgr.sendAlarm(AlarmType.RESUME, alarmId, alarmExtraInfo);
                        timer.cancel();
                    }

                }, 50);


            }//end of run


        });

        for (int i = 0; i < count1; i++) {
            t1 = new Thread() {
                @Override
                public void run() {
                    try {
                        cbr.await();
                    } catch (InterruptedException e) {
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }

                    for (int j = 0; j < 10; j++) {

                        alarmMgr.sendAlarm(AlarmType.FAULT, alarmId, alarmExtraInfo);
                    }
                }
            };
            t1.start();
        }


//		alarmMgr.disconnect();

        Random rnd = new Random();
        Timer timer1 = new Timer(true);
        timer1.schedule(new TimerTask() {

            @Override
            public void run() {
                (new Thread() {

                    @Override
                    public void run() {
                        for (int i = 0; i < 20; i++) {
                            alarmMgr.sendAlarm(AlarmType.FAULT, alarmId, alarmExtraInfo);
                        }

                    }

                }).start();

            }

        }, rnd.nextInt(150));

        timer1.schedule(new TimerTask() {

            @Override
            public void run() {
                alarmMgr.shutdown();

            }

        }, 5000);
    }
}