package com.github.xincao9.benchmark.core.util;

import com.github.xincao9.benchmark.core.interfaces.Source;
import com.github.xincao9.benchmark.core.service.TaskPoolService;

/**
 *
 * @author 510655387@qq.com
 */
public class SequenceSource implements Source {

    private static final TaskPoolService taskPool = TaskPoolService.getInstance();
    private final int max;

    public SequenceSource(int max) {
        this.max = max;
    }

    @Override
    public int read() {
        for (int i = 1; i <= max; i++) {
            try {
                taskPool.add(i);
            } catch (InterruptedException ex) {
                return -1;
            }
        }
        return max;
    }
}
