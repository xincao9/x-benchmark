package com.github.xincao9.benchmark.core.util;

import java.util.Date;

/**
 *
 * @author 510655387@qq.com
 */
public class Logger {

    public static void info(Object message) {
        System.out.println(String.format("[%s-%s] %s", Thread.currentThread().getName(), new Date().toString(), message));
    }
}
