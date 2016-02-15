package com.gmail.mosoft521.jmtpdp.ch07pc;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class WorkStealingChannel<T> implements WorkStealingEnabledChannel<T> {
    // 受管队列
    private final BlockingDeque<T>[] managedQueues;

    public WorkStealingChannel(BlockingDeque<T>[] managedQueues) {
        this.managedQueues = managedQueues;
    }

    @Override
    public T take(BlockingDeque<T> preferredQueue) throws InterruptedException {

        // 优先从指定的受管队列中取“产品”
        BlockingDeque<T> targetQueue = preferredQueue;
        T product = null;

        // 试图从指定的队列队首取“产品”
        if (null != targetQueue) {
            product = targetQueue.poll();
        }

        int queueIndex = -1;

        while (null == product) {
            queueIndex = (queueIndex + 1) % managedQueues.length;
            targetQueue = managedQueues[queueIndex];
            // 试图从其它受管队列的队尾“窃取”“产品”
            product = targetQueue.pollLast();
            if (preferredQueue == targetQueue) {
                break;
            }
        }

        if (null == product) {
            // 随机”窃取“其它受管队列的”产品“
            queueIndex = (int) (System.currentTimeMillis() % managedQueues.length);
            targetQueue = managedQueues[queueIndex];
            product = targetQueue.takeLast();
            System.out.println("stealed from " + queueIndex + ":" + product);
        }

        return product;
    }

    @Override
    public void put(T product) throws InterruptedException {
        int targetIndex = (product.hashCode() % managedQueues.length);
        BlockingQueue<T> targetQueue = managedQueues[targetIndex];
        targetQueue.put(product);
    }

    @Override
    public T take() throws InterruptedException {
        return take(null);
    }
}