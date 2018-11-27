package com.github.xincao9.benchmark.core.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 *
 * @author 510655387@qq.com
 */
public class TaskPoolService {

    private static TaskPoolService taskPool;

    private TaskPoolService() {
    }

    public static TaskPoolService getInstance() {
        if (taskPool == null) {
            taskPool = new TaskPoolService();
        }
        return taskPool;
    }

    private final BlockingQueue<Object> tasks = new LinkedBlockingDeque<Object>();

    public Object remove() throws InterruptedException {
        return tasks.take();
    }

    public void add(Object ps) throws InterruptedException {
        tasks.put(ps);
    }

    public int size() {
        return tasks.size();
    }
}
