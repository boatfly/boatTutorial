package com.boatfly.java8.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 将SimpleDateFormat转为线程安全，上锁
 */
public class DateFormatThreadLocal {
    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }
    };

    public static Date convert(String datestr) throws ParseException {
        return df.get().parse(datestr);
    }
}
