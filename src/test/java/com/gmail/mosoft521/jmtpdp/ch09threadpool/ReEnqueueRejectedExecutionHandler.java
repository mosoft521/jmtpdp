package com.gmail.mosoft521.jmtpdp.ch09threadpool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 该线程池饱和处理策略支持将提交失败的任务重新放入线程池工作队列。
 *
 * @author Viscent Huang
 */
public class ReEnqueueRejectedExecutionHandler implements
        RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if (executor.isShutdown()) {
            return;
        }

        try {
            executor.getQueue().put(r);
        } catch (InterruptedException e) {
            ;
        }
    }
}