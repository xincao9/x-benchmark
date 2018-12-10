package com.github.xincao9.benchmark.core;

import com.github.xincao9.benchmark.core.annotation.Test;
import com.github.xincao9.benchmark.core.interfaces.Method;
import com.github.xincao9.benchmark.core.interfaces.Result;
import com.github.xincao9.benchmark.core.interfaces.Source;
import com.github.xincao9.benchmark.core.model.Report;
import com.github.xincao9.benchmark.core.service.MethodService;
import com.github.xincao9.benchmark.core.service.TaskPoolService;
import com.github.xincao9.benchmark.core.threads.Checker;
import com.github.xincao9.benchmark.core.threads.Worker;
import com.github.xincao9.benchmark.core.util.Logger;
import com.github.xincao9.benchmark.core.web.APIClient;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 510655387@qq.com
 */
public class XBenchmarkCore {

    private static final MethodService methodService = MethodService.getInstance();

    public static void bootstrap(Source source, String host, int port, String username, String passwd, String topic, String title, String... args) {
        APIClient apic = new APIClient(host, port, username, passwd, topic, title);
        bootstrap(source, apic, args);
    }

    public static void bootstrap(Source source, String... args) {
        bootstrap(source, null, args);
    }

    public static void bootstrap(Source source, Result result, String... args) {
        if (source == null) {
            help();
            return;
        }
        int c = 1;
        long t = 50;
        String m = "";
        if (args != null && args.length != 0) {
            if (args.length % 2 != 0) {
                help();
                return;
            } else {
                Map<String, String> params = new HashMap<String, String>();
                for (int i = 0; i < args.length; i += 2) {
                    params.put(args[i], args[i + 1]);
                }
                if (params.containsKey("-c")) {
                    c = Integer.valueOf(params.get("-c"));
                    if (c < 0 || c > 1024) {
                        help();
                        return;
                    }
                }
                if (params.containsKey("-t")) {
                    t = Long.valueOf(params.get("-t"));
                }
                if (params.containsKey("-m")) {
                    m = params.get("-m");
                    if (m == null || "".equals(m)) {
                        help();
                        return;
                    }
                }
            }
        } else {
            help();
            return;
        }
        Method method = methodService.getMethod(m);
        if (method == null) {
            help();
            return;
        }
        bootstrap(source, method, result, c, t);
        Report.getInstance().setStartTime(System.currentTimeMillis());
    }

    public static void bootstrap(Source source, Method method, Result result, int concurrent, long cd) {
        int total = source.read();
        if (total <= 0) {
            Logger.info("你的测试数据为空或者使用中存在错误,请核实后,再运行");
            return;
        }
        Report.getInstance().setTotal(total);
        Report.getInstance().setConcurrent(concurrent);
        Worker.start(concurrent, cd, method);
        Checker.start(result);
    }

    @Test(name = "test")
    public static class MethodD extends Method {

        @Override
        public void exec(Object params) {
            D d = (D) params;
            Logger.info(d.getId());
        }

    }

    public static class SourceD implements Source {

        private static final TaskPoolService taskPool = TaskPoolService.getInstance();

        @Override
        public int read() {
            for (int i = 1; i < 5000; i++) {
                try {
                    taskPool.add(new D(i));
                } catch (InterruptedException ex) {
                }
            }
            return 5000;
        }

    }

    public static class D {

        private int id;

        public D(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }
    }

    public static void help() {
        Logger.info("1.cmd -[c, t, m] value");
        Logger.info("2.com.github.xincao9.benchmark.core.interfaces.Source 接口必须实现, 实现为读取数据源");
        Logger.info("3.com.github.xincao9.benchmark.core.interfaces.Method 接口必须实现且需要使用@Test 标识, 实现为需要测试的代码块");
        Logger.info("4.com.github.xincao9.benchmark.core.interfaces.Result 接口不必须实现, 通过它可以将测试结果输出到自己的系统中");
        Logger.info("5.-c 并发数限制 0 < concurrent <= 1024 默认 1");
        Logger.info("6.-t 请求延时限制 cd > 0 默认 50ms; 建议阻塞调用设置小点, 计算密集调用设置大点, 小于0 为永不延时");
        Logger.info("7.-m 测试的方法类");
        Logger.info("可以测试的方法有 : " + methodService.getMethodNames().toString());
    }
}
