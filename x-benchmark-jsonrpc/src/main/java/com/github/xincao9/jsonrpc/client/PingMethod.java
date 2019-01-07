/*
 * Copyright 2019 Pivotal Software, Inc..
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
package com.github.xincao9.jsonrpc.client;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.xincao9.benchmark.core.annotation.Test;
import com.github.xincao9.benchmark.core.interfaces.Method;
import com.github.xincao9.benchmark.core.util.Logger;
import com.github.xincao9.jsonrpc.Request;
import com.github.xincao9.jsonrpc.Response;
import com.github.xincao9.jsonrpc.XBenchmarkJsonRPC;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author xincao9@gmail.com
 */
@Test(name = "ping")
public class PingMethod extends Method {

    @Override
    public void exec(Object params) throws Exception {
        try {
            String value = RandomStringUtils.randomAscii(128);
            Request request = Request.createRequest(Boolean.TRUE, "ping", Collections.singletonList(value));
            request.setHost("127.0.0.1");
            request.setPort((short) 12306);
            Response<List<Object>> response = XBenchmarkJsonRPC.getJsonRPCClient().invoke(request);
            Logger.info(JSONObject.toJSONString(response, SerializerFeature.DisableCircularReferenceDetect));
        } catch (Throwable e) {
        }
    }

}
