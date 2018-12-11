压测平台支持两种压测模式

1. x-benchmark-core 提供的压测框架进行施压，启动时需要-Dmode=true 或者 默认为 mode=true

java -jar target/x-benchmark-redis-1.0.jar -c 16 -m get -t -1
java -Dmode=false -jar target/x-benchmark-redis-1.0.jar -c 16 -m get -t -1

其他相关的配置
[main-Tue Dec 11 15:21:56 CST 2018] Scanning  annotation, this may take a while, please wait...
[main-Tue Dec 11 15:21:56 CST 2018] Found Method, name=get, Method.class=com.github.xincao9.redis.method.GetMethod
[main-Tue Dec 11 15:21:56 CST 2018] Found Method, name=set, Method.class=com.github.xincao9.redis.method.SetMethod
[main-Tue Dec 11 15:21:56 CST 2018] Finished scan for annotation, found {2} methods(s), cost={49} ms
[main-Tue Dec 11 15:21:56 CST 2018] 1.cmd -[c, t, m] value
[main-Tue Dec 11 15:21:56 CST 2018] 2.com.github.xincao9.benchmark.core.interfaces.Source 接口必须实现, 实现为读取数据源
[main-Tue Dec 11 15:21:56 CST 2018] 3.com.github.xincao9.benchmark.core.interfaces.Method 接口必须实现且需要使用@Test 标识, 实现为需要测试的代码块
[main-Tue Dec 11 15:21:56 CST 2018] 4.com.github.xincao9.benchmark.core.interfaces.Result 接口不必须实现, 通过它可以将测试结果输出到自己的系统中
[main-Tue Dec 11 15:21:56 CST 2018] 5.-c 并发数限制 0 < concurrent <= 1024 默认 1
[main-Tue Dec 11 15:21:56 CST 2018] 6.-t 请求延时限制 cd > 0 默认 50ms; 建议阻塞调用设置小点, 计算密集调用设置大点, 小于0 为永不延时
[main-Tue Dec 11 15:21:56 CST 2018] 7.-m 测试的方法类
[main-Tue Dec 11 15:21:56 CST 2018] 可以测试的方法有 : [set, get]

2. 暴露给wrk施压工具，api接口

启动命令: java -Dmode=false -jar target/x-benchmark-redis-1.0.jar

接口列表
/get
/set

wrk 进行施压

wrk -c 512 -t 16 -d 30 --latency "http://localhost:8888/get"
