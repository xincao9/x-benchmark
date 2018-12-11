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
package com.github.xincao9.memcached.http;

import com.github.xincao9.memcached.XBenchmarkMemcached;
import java.util.concurrent.atomic.AtomicLong;
import net.rubyeye.xmemcached.MemcachedClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author xincao9@gmail.com
 */
@RestController
public class MethodController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodController.class);

    private final AtomicLong setCounter = new AtomicLong(0);
    private final AtomicLong getCounter = new AtomicLong(0);

    @RequestMapping("/get")
    public String get() throws Throwable {
        MemcachedClient memcachedClient = XBenchmarkMemcached.getMemcachedClient();
        String key = String.valueOf(getCounter.incrementAndGet());
        return memcachedClient.get(key);
    }

    @RequestMapping("/set")
    public void set() throws Throwable {
        MemcachedClient memcachedClient = XBenchmarkMemcached.getMemcachedClient();
        String key = String.valueOf(setCounter.incrementAndGet());
        String value = RandomStringUtils.randomAscii(128);
        memcachedClient.set(key, 0, value);
        LOGGER.info(String.format("key = %s, value = %s", key, value));
    }

}
