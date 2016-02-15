package com.gmail.mosoft521.jmtpdp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class AppWrapper {

    public static void main(String[] args) throws FileNotFoundException {
        String[] passArgs = new String[args.length - 2];
        passArgs = Arrays.copyOfRange(args, 3, args.length);

        int preSleep = Integer.valueOf(args[0]);
        int postSleep = Integer.valueOf(args[1]);
        String destClass = args[2];

        String stdInFile = System.getProperty("std.in");
        if (null != stdInFile) {
            System.setIn(new FileInputStream(new File(stdInFile)));
        }

        try {
            Thread.sleep(preSleep);
        } catch (InterruptedException e) {
        }


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            Method method = Class.forName(destClass).getMethod("main", String[].class);

            System.out.println("Started at:" + sdf.format(new Date()));
            method.invoke(null, new Object[]{passArgs});
        } catch (SecurityException e1) {
            e1.printStackTrace();
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println("Finished at:" + sdf.format(new Date()));

        try {
            Thread.sleep(postSleep);
        } catch (InterruptedException e) {
        }
    }
}
/*
todo:err
 */