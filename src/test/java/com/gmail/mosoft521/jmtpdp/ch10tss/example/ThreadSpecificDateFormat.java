package com.gmail.mosoft521.jmtpdp.ch10tss.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadSpecificDateFormat {
    private static final ThreadLocal<SimpleDateFormat> TS_SDF = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat();
        }
    };

    public static Date parse(String timeStamp, String format)
            throws ParseException {
        final SimpleDateFormat sdf = TS_SDF.get();
        sdf.applyPattern(format);
        Date date = sdf.parse(timeStamp);
        return date;
    }

    public static void main(String[] args) throws ParseException {
        Date date = ThreadSpecificDateFormat.parse("20150501123040",
                "yyyyMMddHHmmss");
        System.out.println(date);
    }
}
/*
Fri May 01 12:30:40 CST 2015
 */