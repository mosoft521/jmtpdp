package com.gmail.mosoft521.jmtpdp.ch09threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDeadLockAvoidance {
    private final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            1,
            //最大线程池大小为1（有限数值）：
            1,
            60, TimeUnit.SECONDS,
            //工作队列为SynchronousQueue：
            new SynchronousQueue<Runnable>(),
            //线程池饱和处理策略为CallerRunsPolicy：
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) {
        ThreadPoolDeadLockAvoidance me = new ThreadPoolDeadLockAvoidance();
        me.test("<This will NOT deadlock>");
    }

    public void test(final String message) {
        Runnable taskA = new Runnable() {

            @Override
            public void run() {
                System.out.println("Executing TaskA...");
                Runnable taskB = new Runnable() {

                    @Override
                    public void run() {
                        System.out.println("TaskB processes " + message);
                    }

                };
                Future<?> result = threadPool.submit(taskB);

                try {
                    // 等待TaskB执行结束才能继续执行TaskA，使TaskA和TaskB称为由依赖关系的两个任务
                    result.get();
                } catch (InterruptedException e) {
                    ;
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                System.out.println("TaskA Done.");

            }
        };
        threadPool.submit(taskA);
    }
}
/*
Executing TaskA...
TaskB processes <This will NOT deadlock>
TaskA Done.
死锁了?
 */