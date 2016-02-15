package com.gmail.mosoft521.jmtpdp.ch06promise;

import java.util.HashMap;
import java.util.Map;

public class CaseRunner {

    public static void main(String[] args) {
        Map<String, String> params = new HashMap<String, String>();

        // FTP服务器IP地址，运行代码时请根据实际情况修改！
        params.put("server", "192.168.1.105");

        // FTP账户名，运行代码时请根据实际情况修改！
        params.put("userName", "datacenter");

        // FTP账户密码，运行代码时请根据实际情况修改！
        params.put("password", "abc123");

        DataSyncTask dst = new DataSyncTask(params);
        Thread t = new Thread(dst);
        t.start();
    }
}
/*
死锁了?
 */