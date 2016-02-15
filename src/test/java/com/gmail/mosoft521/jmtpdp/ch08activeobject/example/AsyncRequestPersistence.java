package com.gmail.mosoft521.jmtpdp.ch08activeobject.example;

import org.apache.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

//模式角色：ActiveObject.Proxy
public class AsyncRequestPersistence implements RequestPersistence {
    private static final long ONE_MINUTE_IN_SECONDS = 60;
    private final Logger logger;
    private final AtomicLong taskTimeConsumedPerInterval = new AtomicLong(0);
    private final AtomicInteger requestSubmittedPerIterval = new AtomicInteger(0);

    //模式角色：ActiveObject.Servant
    private final DiskbasedRequestPersistence delegate = new DiskbasedRequestPersistence();

    //模式角色：ActiveObject.Scheduler
    private final ThreadPoolExecutor scheduler;

    //私有构造器
    private AsyncRequestPersistence() {
        logger = Logger.getLogger(AsyncRequestPersistence.class);
        scheduler = new ThreadPoolExecutor(1, 3, 60 * ONE_MINUTE_IN_SECONDS,
                TimeUnit.SECONDS,
                //模式角色：ActiveObject.ActivationQueue
                new ArrayBlockingQueue<Runnable>(200), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t;
                t = new Thread(r, "AsyncRequestPersistence");
                return t;
            }

        });

        scheduler
                .setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 启动队列监控定时任务
        Timer monitorTimer = new Timer(true);
        monitorTimer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                if (logger.isInfoEnabled()) {

                    logger.info("task count:" + requestSubmittedPerIterval
                            + ",Queue size:" + scheduler.getQueue().size()
                            + ",taskTimeConsumedPerInterval:"
                            + taskTimeConsumedPerInterval.get() + " ms");
                }

                taskTimeConsumedPerInterval.set(0);
                requestSubmittedPerIterval.set(0);

            }
        }, 0, ONE_MINUTE_IN_SECONDS * 1000);

    }

    //获取类AsyncRequestPersistence的唯一实例
    public static RequestPersistence getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public void store(final MMSDeliverRequest request) {
        /*
         * 将对store方法的调用封装成MethodRequest对象, 并存入缓冲区。
		 */
        //模式角色：ActiveObject.MethodRequest
        Callable<Boolean> methodRequest = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                long start = System.currentTimeMillis();
                try {
                    delegate.store(request);
                } finally {
                    taskTimeConsumedPerInterval.addAndGet(System.currentTimeMillis()
                            - start);
                }

                return Boolean.TRUE;
            }

        };
        scheduler.submit(methodRequest);

        requestSubmittedPerIterval.incrementAndGet();
    }

    //用于保存AsyncRequestPersistence的唯一实例
    private static class InstanceHolder {
        final static RequestPersistence INSTANCE = new AsyncRequestPersistence();
    }
}