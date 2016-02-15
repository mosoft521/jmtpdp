package com.gmail.mosoft521.jmtpdp.ch12ms.example.testdatagen;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class SmsRequestFactory implements RequestFactory {
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
                entry.interfaceName = "SMS";
                entry.operationName = "sendSms";
                entry.srcDevice = "OSG";
                entry.dstDevice = "ESB";
                DecimalFormat df = new DecimalFormat("0000000");

                int traceId = seq.getAndIncrement();

                entry.traceId = "0020" + df.format(traceId);

                logger.printLog(entry);

                Random rnd = new Random();
                entry.timeStamp += rnd.nextInt(50);
                entry.recordType = "response";
                entry.operationName = "sendSmsRsp";
                entry.srcDevice = "ESB";
                entry.dstDevice = "OSG";
                //entry.traceId="2001"+df.format(traceId+1);
                logger.printLog(entry);


                rnd = new Random();
                entry.timeStamp += rnd.nextInt(50);
                entry.recordType = "request";
                entry.operationName = "sendSms";
                entry.srcDevice = "ESB";
                entry.dstDevice = "NIG";
                traceId = traceId + 1;
                entry.traceId = "0021" + df.format(traceId);
                logger.printLog(entry);


                rnd = new Random();
                entry.timeStamp += rnd.nextInt(300);
                entry.recordType = "response";
                entry.operationName = "sendSmsRsp";
                entry.srcDevice = "NIG";
                entry.dstDevice = "ESB";
                traceId = traceId + 3;
                entry.traceId = "0021" + df.format(traceId);
                logger.printLog(entry);
            }
        };
    }
}