/*
 * Copyright 2018 Pivotal Software, Inc..
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
package com.github.xincao9.redis.http;

import com.github.xincao9.redis.XBenchmarkRedis;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *
 * @author xincao9@gmail.com
 */
@RestController
public class MethodController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodController.class);

    private final AtomicLong getCounter = new AtomicLong(0);
    private final AtomicLong setCounter = new AtomicLong(0);

    @RequestMapping("/get")
    public String get() {
        String key = String.valueOf(getCounter.incrementAndGet());
        JedisPool jedisPool = XBenchmarkRedis.getJedisPool();
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        }
    }

    @RequestMapping("/set")
    public String set() {
        String key = String.valueOf(setCounter.incrementAndGet());
        JedisPool jedisPool = XBenchmarkRedis.getJedisPool();
        try (Jedis jedis = jedisPool.getResource()) {
            String value = RandomStringUtils.randomAscii(128);
            return jedis.set(key, value);
        }
    }
}
