/**
 * 欢迎浏览和修改代码，有任何想法可以email我
 */
package com.github.xincao9.redis.util;

import com.github.xincao9.benchmark.core.util.Logger;
import com.github.xincao9.redis.model.Redis;

/**
 *
 * @author 510655387@qq.com
 */
public class RedisUtils {

    private static RedisUtils redisUtils;
    private static Object lock = new Object();

    public static RedisUtils getInstance(Redis redis) {
        if (redisUtils == null) {
            synchronized (lock) {
                if (redisUtils == null) {
                    if (redis.getAccount() == null || redis.getAccount().isEmpty()) {
                        redisUtils = new RedisUtils(redis.getAddress());
                    } else {
                        redisUtils = new RedisUtils(redis.getAddress(), redis.getAccount(), redis.getPasswd());
                    }
                }
            }
        }
        return redisUtils;
    }

    private RedisUtils(String address, String account, String passwd) {
        try {
        } catch (Throwable e) {
            Logger.info(e.getMessage());
        }
    }

    private RedisUtils(String address) {
        try {
        } catch (Throwable e) {
            Logger.info(e.getMessage());
        }
    }

    public void set(String key, String value) throws Exception {
    }

    public String get(String key) throws Exception {
        return "";
    }

    public void close() {
    }
}
