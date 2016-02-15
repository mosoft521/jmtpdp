package com.gmail.mosoft521.jmtpdp.ch07pc.exmaple;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CaseRunner {

    public static void main(String[] args) {
        AttachmentProcessor ap = new AttachmentProcessor();
        ap.init();
        InputStream in = new ByteArrayInputStream("Hello".getBytes());
        try {
            ap.saveAttachment(in, "000887282", "测试文档");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
Exception in thread "main" java.lang.SecurityException: Invalid originalFileName:测试文档
	at com.gmail.mosoft521.jmtpdp.ch07pc.exmaple.AttachmentProcessor.saveAsFile(AttachmentProcessor.java:83)
	at com.gmail.mosoft521.jmtpdp.ch07pc.exmaple.AttachmentProcessor.saveAttachment(AttachmentProcessor.java:66)
	at com.gmail.mosoft521.jmtpdp.ch07pc.exmaple.CaseRunner.main(CaseRunner.java:14)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at com.intellij.rt.execution.application.AppMain.main(AppMain.java:144)
 */