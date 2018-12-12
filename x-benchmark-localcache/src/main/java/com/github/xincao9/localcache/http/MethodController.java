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
package com.github.xincao9.localcache.http;

import com.github.xincao9.localcache.GuavaCacheSupport;
import com.github.xincao9.localcache.JCSSupport;
import com.github.xincao9.localcache.XBenchmarkLocalcache;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author xincao9@gmail.com
 */
@RestController
public class MethodController {

    private final AtomicLong getCounter = new AtomicLong(0);
    private final AtomicLong setCounter = new AtomicLong(0);

    @RequestMapping("/get")
    public String get() {
        String key = String.valueOf(getCounter.incrementAndGet());
        if ("jcs".equalsIgnoreCase(XBenchmarkLocalcache.getType())) {
            return JCSSupport.get(key);
        } else if ("guava".equalsIgnoreCase(XBenchmarkLocalcache.getType())) {
            return GuavaCacheSupport.get(key);
        } else {
            return "";
        }
    }

    @RequestMapping("/set")
    public void set() {
        String key = String.valueOf(setCounter.incrementAndGet());
        String value = RandomStringUtils.randomAscii(128);
        if ("jcs".equalsIgnoreCase(XBenchmarkLocalcache.getType())) {
            JCSSupport.put(key, value);
        } else if ("guava".equalsIgnoreCase(XBenchmarkLocalcache.getType())) {
            GuavaCacheSupport.put(key, value);
        }
    }

    @RequestMapping("/setget")
    public String setget() {
        String key = String.valueOf(setCounter.incrementAndGet());
        String value = RandomStringUtils.randomAscii(128);
        if ("jcs".equalsIgnoreCase(XBenchmarkLocalcache.getType())) {
            JCSSupport.put(key, value);
            return JCSSupport.get(key);
        } else if ("guava".equalsIgnoreCase(XBenchmarkLocalcache.getType())) {
            GuavaCacheSupport.put(key, value);
            return GuavaCacheSupport.get(key);
        } else {
            return "";
        }

    }

}
