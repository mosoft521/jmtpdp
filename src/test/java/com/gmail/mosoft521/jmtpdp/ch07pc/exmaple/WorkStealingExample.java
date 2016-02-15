package com.gmail.mosoft521.jmtpdp.ch07pc.exmaple;


import com.gmail.mosoft521.jmtpdp.ch05tpt.AbstractTerminatableThread;
import com.gmail.mosoft521.jmtpdp.ch05tpt.TerminationToken;
import com.gmail.mosoft521.jmtpdp.ch07pc.WorkStealingChannel;
import com.gmail.mosoft521.jmtpdp.ch07pc.WorkStealingEnabledChannel;

import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 工作窃取算法示例。 该类使用Two-phase Termination模式（参见第5章）。
 *
 * @author Viscent Huang
 */
public class WorkStealingExample {
    private final WorkStealingEnabledChannel<String> channel;
    private final TerminationToken token = new TerminationToken();

    public WorkStealingExample() {
        int nCPU = Runtime.getRuntime().availableProcessors();
        int consumerCount = nCPU / 2 + 1;

        @SuppressWarnings("unchecked")
        BlockingDeque<String>[] managedQueues
                = new LinkedBlockingDeque[consumerCount];

        // 该通道实例对应了多个队列实例managedQueues
        channel = new WorkStealingChannel<String>(managedQueues);

        Consumer[] consumers = new Consumer[consumerCount];
        for (int i = 0; i < consumerCount; i++) {
            managedQueues[i] = new LinkedBlockingDeque<String>();
            consumers[i] = new Consumer(token, managedQueues[i]);

        }

        for (int i = 0; i < nCPU; i++) {
            new Producer().start();
        }

        for (int i = 0; i < consumerCount; i++) {

            consumers[i].start();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        WorkStealingExample wse;
        wse = new WorkStealingExample();

        wse.doSomething();
        Thread.sleep(3500);

    }

    public void doSomething() {

    }

    private class Producer extends AbstractTerminatableThread {
        private int i = 0;

        @Override
        protected void doRun() throws Exception {
            channel.put(String.valueOf(i++));
            token.reservations.incrementAndGet();
        }

    }

    private class Consumer extends AbstractTerminatableThread {
        private final BlockingDeque<String> workQueue;

        public Consumer(TerminationToken token, BlockingDeque<String> workQueue) {
            super(token);
            this.workQueue = workQueue;
        }

        @Override
        protected void doRun() throws Exception {

			/*
             * WorkStealingEnabledChannel接口的take(BlockingDequepreferedQueue)方法
			 * 实现了工作窃取算法
			 */
            String product = channel.take(workQueue);

            System.out.println("Processing product:" + product);

            // 模拟执行真正操作的时间消耗
            try {
                Thread.sleep(new Random().nextInt(50));
            } catch (InterruptedException e) {
                ;
            } finally {
                token.reservations.decrementAndGet();
            }
        }
    }
}
/*
Processing product:0
Processing product:1
Processing product:2
Processing product:3
Processing product:5
Processing product:8
Processing product:4
Processing product:6
Processing product:7
Processing product:11
Processing product:14
Processing product:10
Processing product:9
Processing product:17
Processing product:20
Processing product:13
Processing product:23
Processing product:16
Processing product:19
Processing product:12
Processing product:26
Processing product:15
Processing product:29
Processing product:22
Processing product:32
Processing product:18
Processing product:21
Processing product:35
Processing product:25
Processing product:24
Processing product:28
Processing product:38
Processing product:27
Processing product:31
Processing product:30
Processing product:33
Processing product:41
Processing product:34
Processing product:44
Processing product:36
Processing product:37
Processing product:40
Processing product:43
Processing product:39
Processing product:47
Processing product:42
Processing product:50
Processing product:46
Processing product:45
Processing product:48
Processing product:53
Processing product:49
Processing product:51
Processing product:56
Processing product:54
Processing product:59
Processing product:52
Processing product:57
Processing product:55
Processing product:60
Processing product:62
Processing product:65
Processing product:58
Processing product:68
Processing product:61
Processing product:63
Processing product:71
 */