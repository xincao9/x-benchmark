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
package com.github.xincao9.localcache;

import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.CacheAccess;

/**
 *
 * @author xincao9@gmail.com
 */
public class JCSSupport {

    private static final CacheAccess<String, String> CACHE_ACCESS;

    static {
        CACHE_ACCESS = JCS.getInstance("cacheAccess");
    }

    public static String get(String key) {
        return CACHE_ACCESS.get(key);
    }

    public static void put(String key, String value) {
        CACHE_ACCESS.put(key, value);
    }
}
