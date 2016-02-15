package com.gmail.mosoft521.jmtpdp.ch11stc;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;

/**
 * Serial Thread Confinement模式Serializer参与者可复用实现。
 *
 * @param <T> Serializer向WorkerThread所提交的任务对应的类型
 * @param <V> service方法的返回值类型
 * @author Viscent Huang
 */
public abstract class AbstractSerializer<T, V> {
    private final TerminatableWorkerThread<T, V> workerThread;

    public AbstractSerializer(BlockingQueue<Runnable> workQueue,
                              TaskProcessor<T, V> taskProcessor) {
        workerThread = new TerminatableWorkerThread<T, V>(workQueue, taskProcessor);
    }

    /**
     * 留给子类实现。用于根据指定参数生成相应的任务实例。
     *
     * @param params 参数列表
     * @return 任务实例。用于提交给WorkerThread。
     */
    protected abstract T makeTask(Object... params);

    /**
     * 该类对外暴露的服务方法。 该类的子需要定义一个命名含义比该方法更为具体的方法（如downloadFile）。
     * 含义具体的服务方法（如downloadFile）可直接调用该方法。
     *
     * @param params 客户端代码调用该方法时所传递的参数列表
     * @return 可借以获取任务处理结果的Promise（参见第6章，Promise模式）实例。
     * @throws InterruptedException
     */
    protected Future<V> service(Object... params) throws InterruptedException {
        T task = makeTask(params);
        Future<V> resultPromise = workerThread.submit(task);

        return resultPromise;
    }

    /**
     * 初始化该类对外暴露的服务。
     */
    public void init() {
        workerThread.start();
    }

    /**
     * 停止该类对外暴露的服务。
     */
    public void shutdown() {
        workerThread.terminate();
    }
}