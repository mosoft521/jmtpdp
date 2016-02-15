package com.gmail.mosoft521.jmtpdp.ch13pipeline;

import java.util.concurrent.TimeUnit;

/**
 * Pipe的抽象实现类。
 * 该类会调用其子类实现的doProcess方法对输入元素进行处理。并将相应的输出作为
 * 下一个Pipe实例的输入。
 *
 * @param <IN>  输入类型
 * @param <OUT> 输出类型
 * @author Viscent Huang
 */
public abstract class AbstractPipe<IN, OUT> implements Pipe<IN, OUT> {
    protected volatile Pipe<?, ?> nextPipe = null;
    protected volatile PipeContext pipeCtx;

    @Override
    public void init(PipeContext pipeCtx) {
        this.pipeCtx = pipeCtx;

    }

    @Override
    public void setNextPipe(Pipe<?, ?> nextPipe) {
        this.nextPipe = nextPipe;

    }

    @Override
    public void shutdown(long timeout, TimeUnit unit) {
        // 什么也不做
    }

    /**
     * 留给子类实现。用于子类实现其任务处理逻辑。
     *
     * @param input 输入元素（任务）
     * @return 任务的处理结果
     * @throws PipeException
     */
    protected abstract OUT doProcess(IN input) throws PipeException;

    @SuppressWarnings("unchecked")
    public void process(IN input) throws InterruptedException {

        try {

            OUT out = doProcess(input);
            if (null != nextPipe) {
                if (null != out) {
                    ((Pipe<OUT, ?>) nextPipe).process(out);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (PipeException e) {
            pipeCtx.handleError(e);
        }
    }
}