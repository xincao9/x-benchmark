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
package com.github.xincao9.memcached.method;

import com.github.xincao9.benchmark.core.annotation.Test;
import com.github.xincao9.benchmark.core.interfaces.Method;
import com.github.xincao9.benchmark.core.util.Logger;
import com.github.xincao9.memcached.XBenchmarkMemcached;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.MemcachedClient;

/**
 *
 * @author xincao9@gmail.com
 */
@Test(name = "cas")
public class CasMethod extends Method {

    public CasMethod() {
        try {
            MemcachedClient memcachedClient = XBenchmarkMemcached.getMemcachedClient();
            memcachedClient.set("cas", 0, "0");
        } catch (Throwable e) {
            Logger.info(e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public void exec(Object params) throws Exception {
        MemcachedClient memcachedClient = XBenchmarkMemcached.getMemcachedClient();
        GetsResponse<String> getsResponse = memcachedClient.gets("cas");
        Logger.info(memcachedClient.cas("cas", 0, String.valueOf(Long.valueOf(getsResponse.getValue()) + 1), getsResponse.getCas()));
    }

}
