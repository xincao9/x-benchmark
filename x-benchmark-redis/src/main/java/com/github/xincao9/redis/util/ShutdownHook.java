/**
 * 欢迎浏览和修改代码，有任何想法可以email我
 */
package com.github.xincao9.redis.util;

import com.github.xincao9.redis.model.Configure;


/**
 *
 * @author 510655387@qq.com
 */
public class ShutdownHook extends Thread {

    @Override
    public void run() {
        RedisUtils.getInstance(Configure.getConfigure().getRedis()).close();
    }

}
