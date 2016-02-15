package com.gmail.mosoft521.jmtpdp.ch12ms;

import java.util.concurrent.Callable;

/**
 * 对处理失败的子任务进行重试所需的信息。
 *
 * @param <T> 子任务类型
 * @param <V> 子任务处理结果类型
 * @author Viscent Huang
 */
public class RetryInfo<T, V> {
    public final T subTask;
    public final Callable<V> redoCommand;

    public RetryInfo(T subTask, Callable<V> redoCommand) {
        this.subTask = subTask;
        this.redoCommand = redoCommand;
    }
}