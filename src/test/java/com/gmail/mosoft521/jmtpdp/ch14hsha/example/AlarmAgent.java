package com.gmail.mosoft521.jmtpdp.ch14hsha.example;

import com.gmail.mosoft521.jmtpdp.ch04gs.Blocker;
import com.gmail.mosoft521.jmtpdp.ch04gs.ConditionVarBlocker;
import com.gmail.mosoft521.jmtpdp.ch04gs.GuardedAction;
import com.gmail.mosoft521.jmtpdp.ch04gs.Predicate;
import com.gmail.mosoft521.jmtpdp.util.Debug;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

/**
 * 负责连接告警服务器，并发送告警信息至告警服务器
 */
public class AlarmAgent {
    // 模式角色：GuardedSuspension.Blocker
    private final Blocker blocker = new ConditionVarBlocker();
    // 心跳定时器
    private final Timer heartbeatTimer = new Timer(true);
    // 用于记录AlarmAgent是否连接上告警服务器
    private volatile boolean connectedToServer = false;
    // 模式角色：GuardedSuspension.Predicate
    private final Predicate agentConnected = new Predicate() {
        @Override
        public boolean evaluate() {
            return connectedToServer;
        }
    };

    // 省略其它代码

    /**
     * 发送告警信息
     *
     * @param alarm 告警信息
     * @throws Exception
     */
    public void sendAlarm(final AlarmInfo alarm) throws Exception {
        // 可能需要等待，直到AlarmAgent连接上告警服务器（或者连接中断后重新连连上服务器）
        // 模式角色：GuardedSuspension.GuardedAction
        GuardedAction<Void> guardedAction = new GuardedAction<Void>(agentConnected) {
            public Void call() throws Exception {
                doSendAlarm(alarm);
                return null;
            }
        };

        blocker.callWithGuard(guardedAction);
    }

    // 通过网络连接将告警信息发送给告警服务器
    private void doSendAlarm(AlarmInfo alarm) {
        // 省略其它代码
        Debug.info("sending alarm " + alarm);

        // 模拟发送告警至服务器的耗时
        try {
            Thread.sleep(50);
        } catch (Exception e) {

        }
    }

    public void init() {
        // 省略其它代码

        // 告警连接线程
        Thread connectingThread = new Thread(new ConnectingTask());

        connectingThread.start();

        heartbeatTimer.schedule(new HeartbeatTask(), 60000, 2000);
    }

    public void disconnect() {
        // 省略其它代码
        Debug.info("disconnected from alarm server.");
        connectedToServer = false;
    }

    protected void onConnected() {
        try {
            blocker.signalAfter(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    connectedToServer = true;
                    Debug.info("connected to server");
                    return Boolean.TRUE;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onDisconnected() {
        connectedToServer = false;
    }

    // 负责与告警服务器建立网络连接
    private class ConnectingTask implements Runnable {
        @Override
        public void run() {
            // 省略其它代码

            // 模拟连接操作耗时
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                ;
            }

            onConnected();
        }
    }

    /**
     * 心跳定时任务：定时检查与告警服务器的连接是否正常，发现连接异常后自动重新连接
     */
    private class HeartbeatTask extends TimerTask {
        // 省略其它代码

        @Override
        public void run() {
            // 省略其它代码

            if (!testConnection()) {
                onDisconnected();
                reconnect();
            }

        }

        private boolean testConnection() {
            // 省略其它代码

            return true;
        }

        private void reconnect() {
            ConnectingTask connectingThread = new ConnectingTask();

            // 直接在心跳定时器线程中执行
            connectingThread.run();
        }
    }
}