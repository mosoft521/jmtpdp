package com.gmail.mosoft521.jmtpdp.ch11stc.example;

public class SampleClient {
    private static final MessageFileDownloader DOWNLOADER;

    static {

        //请根据实际情况修改构造器MessageFileDownloader的参数
        DOWNLOADER = new MessageFileDownloader("/home/viscent/tmp/incoming",                "192.168.1.105", "datacenter", "abc123");
        DOWNLOADER.init();
    }

    public static void main(String[] args) {
        DOWNLOADER.downloadFile("abc.xml");
        // 执行其它操作
    }
}
/*
todo:
 */