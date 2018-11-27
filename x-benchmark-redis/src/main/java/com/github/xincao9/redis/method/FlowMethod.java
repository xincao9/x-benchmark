/**
 * 欢迎浏览和修改代码，有任何想法可以email我
 */
package com.github.xincao9.redis.method;

import com.github.xincao9.benchmark.core.annotation.Test;
import com.github.xincao9.benchmark.core.interfaces.Method;
import com.github.xincao9.benchmark.core.util.Logger;
import com.github.xincao9.redis.constent.SourceType;
import com.github.xincao9.redis.model.Configure;
import com.github.xincao9.redis.model.Request;
import com.github.xincao9.redis.util.RedisUtils;
import java.util.List;

/**
 *
 * @author 510655387@qq.com
 */
@Test(name = "FlowMethod")
public class FlowMethod extends Method {

    @Override
    public void exec(Object params) throws Exception {
        List<Request> requests = Configure.getConfigure().getFlow();
        switch (SourceType.fromName(Configure.getConfigure().getSource().getType())) {
            case FILE: {
                List<String> values = (List<String>) params;
                Logger.info(values.toString());
                for (Request request : requests) {
                    String k = request.getKey();
                    String v = request.getValue();
                    if (v != null && !v.isEmpty()) {
                        if (v.charAt(0) == '%') {
                            Integer index = Integer.valueOf(v.substring(1, v.length()));
                            RedisUtils.getInstance(Configure.getConfigure().getRedis()).set(k, values.get(index));
                        }
                    }
                }
                break;
            }
            case SEQUENCE: {
                Logger.info((Integer) params);
                for (Request request : requests) {
                    RedisUtils.getInstance(Configure.getConfigure().getRedis()).set(request.getKey(), request.getValue());
                }
                break;
            }
            default: {
                Logger.info("不能识别的参数来源");
            }
        }
    }

}
