/*
 * Copyright 2018 xingyunzhi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.xincao9.redis;

import com.github.xincao9.benchmark.core.XBenchmarkCore;
import com.github.xincao9.benchmark.core.util.SequenceSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import redis.clients.jedis.JedisPool;

/**
 *
 * @author xincao9@gmail.com
 */
@SpringBootApplication
public class XBenchmarkRedis {

    private static JedisPool jedisPool;
    private static boolean mode;

    public static void main(String... args) {
        jedisPool = new JedisPool("localhost", 6379);
        mode = Boolean.valueOf(System.getProperty("mode", "true"));
        if (mode) {
            XBenchmarkCore.bootstrap(new SequenceSource(1000000), args);
        } else {
            SpringApplication.run(XBenchmarkRedis.class, args);
        }
    }

    public static JedisPool getJedisPool() {
        return jedisPool;
    }

}
