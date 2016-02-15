package com.gmail.mosoft521.jmtpdp.ch13pipeline;

/**
 * 对各个处理阶段的计算环境进行抽象，主要用于异常处理。
 *
 * @author Viscent Huang
 */
public interface PipeContext {
    /**
     * 用于对处理阶段抛出的异常进行处理.
     *
     * @param exp
     */
    void handleError(PipeException exp);
}