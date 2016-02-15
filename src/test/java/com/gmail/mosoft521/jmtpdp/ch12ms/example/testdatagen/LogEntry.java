package com.gmail.mosoft521.jmtpdp.ch12ms.example.testdatagen;

public class LogEntry {
    public long timeStamp;
    public String interfaceType;
    public String recordType;//request or response
    public String interfaceName;
    public String operationName;
    public String srcDevice = "ESB";
    public String dstDevice;
    public String traceId;
    public String selfIPAddress = "192.168.1.102";
    public String calling = "13612345678";
    public String callee = "136712345670";

    @Override
    public String toString() {
        return "LogEntry [timeStamp=" + timeStamp + ", interfaceType="
                + interfaceType + ", recordType=" + recordType + ", interfaceName="
                + interfaceName + ", operationName=" + operationName + ", srcDevice="
                + srcDevice + ", dstDevice=" + dstDevice + ", traceId=" + traceId
                + ", calling=" + calling + ", callee=" + callee + "]";
    }
}