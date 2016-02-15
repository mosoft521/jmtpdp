package com.gmail.mosoft521.jmtpdp.ch12ms.example.testdatagen;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.SimpleTimeZone;
import java.util.concurrent.atomic.AtomicLong;

public class Logger {

    //接口日志输出目标目录
    private static final String LOG_FILE_BASE_DIR = System
            .getProperty("java.io.tmpdir") + "/tps/";
    private static final String TIME_STAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String TIME_STAMP_FORMAT1 = "yyyyMMddHHmm";
    private int maxLines = 10000;
    private AtomicLong linesCount = new AtomicLong(maxLines);
    private PrintWriter cachedPwr = null;

    private static String getUTCTimeStamp(long timeStamp, String format) {
        SimpleTimeZone stz = new SimpleTimeZone(0, "UTC");
        Calendar calendar = Calendar.getInstance(stz);
        calendar.setTimeInMillis(timeStamp);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(stz);
        String tempTs = sdf.format(calendar.getTime());
        return tempTs;
    }

    public void printLog(LogEntry entry) {
        // System.out.println(entry);
        // IP address
        PrintWriter pwr = this.getWriter();

        pwr.print(getUTCTimeStamp(Long.valueOf(entry.timeStamp), TIME_STAMP_FORMAT));
        pwr.print('|');

        pwr.print(entry.interfaceType);
        pwr.print('|');

        pwr.print(entry.recordType);
        pwr.print('|');

        pwr.print(entry.interfaceName);
        pwr.print('|');

        pwr.print(entry.operationName);
        pwr.print('|');

        pwr.print(entry.srcDevice);
        pwr.print('|');

        pwr.print(entry.dstDevice);
        pwr.print('|');

        pwr.print(entry.traceId);
        pwr.print('|');

        pwr.print(entry.selfIPAddress);
        pwr.print('|');

        pwr.print(entry.calling);
        pwr.print('|');

        pwr.print(entry.callee);
        pwr.println();
        long count = linesCount.incrementAndGet();
        if (count >= maxLines) {
            pwr.flush();
            pwr.close();
        }
    }

    private PrintWriter getWriter() {

        if (linesCount.get() >= maxLines) {
            try {
                cachedPwr = new PrintWriter(LOG_FILE_BASE_DIR + retrieveLogFileName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            linesCount.set(0);
        }
        return cachedPwr;
    }

    private String retrieveLogFileName() {

        return "ESB_interface_"
                + getUTCTimeStamp(System.currentTimeMillis(), TIME_STAMP_FORMAT1)
                + ".log";
    }
}