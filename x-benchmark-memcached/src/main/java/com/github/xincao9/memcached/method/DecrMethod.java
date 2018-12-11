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
import net.rubyeye.xmemcached.MemcachedClient;

/**
 *
 * @author xincao9@gmail.com
 */
@Test(name = "decr")
public class DecrMethod extends Method {

    @Override
    public void exec(Object params) throws Exception {
        MemcachedClient memcachedClient = XBenchmarkMemcached.getMemcachedClient();
        Logger.info(memcachedClient.decr("decr", 1, Long.MAX_VALUE));
    }

}
