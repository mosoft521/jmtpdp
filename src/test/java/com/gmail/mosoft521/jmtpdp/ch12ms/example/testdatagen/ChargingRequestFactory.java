package com.gmail.mosoft521.jmtpdp.ch12ms.example.testdatagen;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ChargingRequestFactory implements RequestFactory {
    private final AtomicInteger seq = new AtomicInteger(0);

    @Override
    public SimulatedRequest newRequest() {
        return new SimulatedRequest() {

            @Override
            public void printLogs(Logger logger) {
                LogEntry entry = new LogEntry();
                entry.timeStamp = System.currentTimeMillis();
                entry.recordType = "request";

                entry.interfaceType = "SOAP";
                entry.interfaceName = "Chg";
                entry.operationName = "getPrice";
                entry.srcDevice = "OSG";
                entry.dstDevice = "ESB";
                DecimalFormat df = new DecimalFormat("0000000");

                int traceId = seq.getAndIncrement();
                int originalTId = traceId;

                entry.traceId = "0020" + df.format(traceId);

                logger.printLog(entry);

                Random rnd = new Random();
                entry.timeStamp += rnd.nextInt(20);
                entry.recordType = "request";
                entry.operationName = "getPrice";
                entry.srcDevice = "ESB";
                entry.dstDevice = "BSS";
                traceId = traceId + 1;
                entry.traceId = "0021" + df.format(traceId);
                logger.printLog(entry);


                rnd = new Random();
                entry.timeStamp += rnd.nextInt(600);
                entry.recordType = "response";
                entry.operationName = "getPriceRsp";
                entry.srcDevice = "BSS";
                entry.dstDevice = "ESB";
                traceId = traceId + 3;
                entry.traceId = "0021" + df.format(traceId);
                logger.printLog(entry);

                rnd = new Random();
                entry.timeStamp += rnd.nextInt(650);
                entry.recordType = "response";
                entry.operationName = "getLocationRsp";
                entry.srcDevice = "ESB";
                entry.dstDevice = "OSG";
                entry.traceId = "0020" + df.format(originalTId + 2);
                logger.printLog(entry);
            }
        };
    }
}
