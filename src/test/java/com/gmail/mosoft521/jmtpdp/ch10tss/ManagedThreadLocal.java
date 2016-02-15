package com.gmail.mosoft521.jmtpdp.ch10tss;

import java.lang.ref.WeakReference;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 支持统一清理不再被使用的ThreadLocal变量的ThreadLocal子类。
 *
 * @param <T> 相应的线程特有对象类型
 * @author Viscent Huang
 */
public class ManagedThreadLocal<T> extends ThreadLocal<T> {

    /*
     * 使用弱引用，防止内存泄漏。
     * 使用volatile修饰保证内存可见性。
     */
    private static volatile Queue<WeakReference<ManagedThreadLocal<?>>> instances = new ConcurrentLinkedQueue<WeakReference<ManagedThreadLocal<?>>>();

    private volatile ThreadLocal<T> threadLocal;

    private ManagedThreadLocal(final InitialValueProvider<T> ivp) {

        this.threadLocal = new ThreadLocal<T>() {

            @Override
            protected T initialValue() {
                return ivp.initialValue();
            }

        };
    }

    public static <T> ManagedThreadLocal<T> newInstance(
            final InitialValueProvider<T> ivp) {
        ManagedThreadLocal<T> mtl = new ManagedThreadLocal<T>(ivp);

        // 使用弱引用来引用ThreadLocalProxy实例，防止内存泄漏。
        instances.add(new WeakReference<ManagedThreadLocal<?>>(mtl));
        return mtl;
    }

    public static <T> ManagedThreadLocal<T> newInstance() {
        return newInstance(new ManagedThreadLocal.InitialValueProvider<T>());
    }

    /**
     * 清理该类所管理的所有ThreadLocal实例。
     */
    public static void removeAll() {
        WeakReference<ManagedThreadLocal<?>> wrMtl;
        ManagedThreadLocal<?> mtl;
        while (null != (wrMtl = instances.poll())) {
            mtl = wrMtl.get();
            if (null != mtl) {
                mtl.remove();
            }
        }
    }

    public T get() {
        return threadLocal.get();
    }

    public void set(T value) {
        threadLocal.set(value);
    }

    public void remove() {
        if (null != threadLocal) {
            threadLocal.remove();
            threadLocal = null;
        }
    }

    public static class InitialValueProvider<T> {
        protected T initialValue() {
            //默认值为null
            return null;
        }
    }
}