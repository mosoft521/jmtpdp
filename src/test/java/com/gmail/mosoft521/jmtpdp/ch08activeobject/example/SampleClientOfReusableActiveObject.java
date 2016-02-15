package com.gmail.mosoft521.jmtpdp.ch08activeobject.example;


import com.gmail.mosoft521.jmtpdp.ch08activeobject.ActiveObjectProxy;
import com.gmail.mosoft521.jmtpdp.util.Debug;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SampleClientOfReusableActiveObject {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        SampleActiveObject sao = ActiveObjectProxy.newInstance(
                SampleActiveObject.class, new SampleActiveObjectImpl(),
                Executors.newCachedThreadPool());
        Future<String> ft = null;

        Debug.info("Before calling active object");
        try {
            ft = sao.process("Something", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //模拟其它操作的时间消耗
        Thread.sleep(40);

        Debug.info(ft.get());
    }
}
/*
[2016-02-15 16:02:38.346][INFO][main]:Before calling active object
[2016-02-15 16:02:38.349][INFO][pool-1-thread-1]:doProcess start
[2016-02-15 16:02:38.399][INFO][main]:Something-1
todo:
 */