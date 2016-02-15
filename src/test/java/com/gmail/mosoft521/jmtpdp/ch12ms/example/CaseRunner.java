package com.gmail.mosoft521.jmtpdp.ch12ms.example;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 本章实战案例运行时所需的接口日志文件可以使用以下类生成：
 * io.github.viscent.mtpattern.ch12.ms.example.testdatagen.TestingDataGen
 *
 * @author Viscent Huang
 */
public class CaseRunner {

    public static void main(String[] args) throws Exception {

        String logFileBaseDir = System.getProperty("java.io.tmpdir") + "/tps/";
        final Pattern pattern;

        String matchingRegExp;

        // 用于选择要进行统计的接口日志文件的正则表达式，请根据实际情况修改。
        matchingRegExp = "20150420131[0-9]";

        pattern = Pattern.compile("ESB_interface_" + matchingRegExp + ".log");
        PipedInputStream pipeIn = new PipedInputStream();
        final PipedOutputStream pipeOut = new PipedOutputStream(pipeIn);

        File dir = new File(logFileBaseDir);
        dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                Matcher matcher = pattern.matcher(name);
                boolean toAccept = matcher.matches();
                if (toAccept) {
                    try {

                        // 向TPSStat输出待统计的接口日志文件名
                        pipeOut.write((name + "\n").getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return toAccept;
            }
        });

        pipeOut.flush();
        pipeOut.close();
        System.setIn(pipeIn);
        TPSStat.main(new String[]{logFileBaseDir});
    }
}