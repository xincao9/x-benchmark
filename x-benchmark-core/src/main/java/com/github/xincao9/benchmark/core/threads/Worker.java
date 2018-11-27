package com.github.xincao9.benchmark.core.threads;

import com.github.xincao9.benchmark.core.interfaces.Method;
import com.github.xincao9.benchmark.core.service.TaskPoolService;
import com.github.xincao9.benchmark.core.util.Logger;

/**
 * 工作线程,用来模拟并发用户
 *
 * @author 510655387@qq.com
 */
public class Worker implements Runnable {

    private static final TaskPoolService taskPool = TaskPoolService.getInstance();
    private static long CD = 50;
    private static int concurrent = 1;
    private static Method METHOD;
    private static Thread[] threads;

    private Worker() {
    }

    public static void start(Method method) {
        METHOD = method;
        threads = new Thread[Worker.concurrent];
        for (int no = 0; no < Worker.concurrent; no++) {
            Thread thread = new Thread(new Worker(), String.format("%s - %d", "工作线程", no));
            thread.start();
        }
    }

    public static void start(int concurrent, long cd, Method method) {
        Worker.concurrent = concurrent;
        Worker.CD = cd;
        METHOD = method;
        threads = new Thread[Worker.concurrent];
        for (int no = 0; no < Worker.concurrent; no++) {
            Thread thread = new Thread(new Worker(), String.format("%s - %d", "工作线程", no));
            thread.start();
            threads[no] = thread;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                METHOD.run(taskPool.remove());
                if (CD > 0) {
                    int time = (int) (Math.random() * CD) + 1;
                    Thread.sleep(time);
                }
            } catch (InterruptedException ex) {
                Logger.info("工作线程关闭完成");
                return;
            }
        }
    }

    public static void close() {
        for (Thread t : threads) {
            t.interrupt();
        }
    }
}
