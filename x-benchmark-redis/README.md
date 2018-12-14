The pressure test platform supports two pressure measurement modes.

The pressure gauge provided by x-benchmark-core is pressed. It needs -Dmode=true or default mode=true at startup.

<pre>
Java -jar target/x-benchmark-redis-1.0.jar -c 16 -m get -t -1
Java -Dmode=false -jar target/x-benchmark-redis-1.0.jar -c 16 -m get -t -1
</pre>

Other related configurations
<pre>
[main-Tue Dec 11 15:21:56 CST 2018] Scanning annotation, this may take a while, please wait...
[main-Tue Dec 11 15:21:56 CST 2018] Found Method, name=get, Method.class=com.github.xincao9.redis.method.GetMethod
[main-Tue Dec 11 15:21:56 CST 2018] Found Method, name=set, Method.class=com.github.xincao9.redis.method.SetMethod
[main-Tue Dec 11 15:21:56 CST 2018] Finished scan for annotation, found {2} methods(s), cost={49} ms
[main-Tue Dec 11 15:21:56 CST 2018] 1.cmd -[c, t, m] value
[main-Tue Dec 11 15:21:56 CST 2018] 2.com.github.xincao9.benchmark.core.interfaces.Source interface must be implemented, implemented as a read data source
[main-Tue Dec 11 15:21:56 CST 2018] 3.com.github.xincao9.benchmark.core.interfaces.Method The interface must be implemented and needs to be identified by the @Test flag, which is implemented as a block of code to be tested.
[main-Tue Dec 11 15:21:56 CST 2018] 4.com.github.xincao9.benchmark.core.interfaces.Result The interface does not have to be implemented, it can output test results to its own system.
[main-Tue Dec 11 15:21:56 CST 2018] 5.-c Concurrency limit 0 < concurrent <= 1024 default 1
[main-Tue Dec 11 15:21:56 CST 2018] 6.-t request delay limit cd > 0 default 50ms; it is recommended to block the call to set the small point, calculate the dense call to set the big point, less than 0 for the never delay
[main-Tue Dec 11 15:21:56 CST 2018] 7.-m test method class
[main-Tue Dec 11 15:21:56 CST 2018] The methods that can be tested are: [set, get]
</pre>

Exposed to wrk pressure tool, api interface

<pre>
Start the command: java -Dmode=false -jar target/x-benchmark-redis-1.0.jar
</pre>

Interface list

* /get
* /set

Wrk pressure

<pre>
Wrk -c 512 -t 16 -d 30 --latency "http://localhost:8888/get"
</pre>
