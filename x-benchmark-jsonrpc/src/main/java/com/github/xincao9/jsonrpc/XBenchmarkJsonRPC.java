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
package com.github.xincao9.jsonrpc;

import com.github.xincao9.benchmark.core.XBenchmarkCore;
import com.github.xincao9.benchmark.core.util.SequenceSource;
import com.github.xincao9.jsonrpc.client.JsonRPCClient;
import com.github.xincao9.jsonrpc.server.JsonRPCServer;
import com.github.xincao9.jsonrpc.server.SayServiceImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author xincao9@gmail.com
 */
@SpringBootApplication
public class XBenchmarkJsonRPC {

    private static boolean mode;
    private static SayService sayService;

    public static void main(String... args) throws Throwable {
        mode = Boolean.valueOf(System.getProperty("mode", "true"));
        if (mode) {
            JsonRPCServer jsonRPCServer = JsonRPCServer.defaultJsonRPCServer();
            jsonRPCServer.register(new SayServiceImpl());
            jsonRPCServer.start();
        } else {
            JsonRPCClient jsonRPCClient = JsonRPCClient.defaultJsonRPCClient();
            jsonRPCClient.start();
            XBenchmarkJsonRPC.sayService = jsonRPCClient.proxy(SayService.class);
            XBenchmarkCore.bootstrap(new SequenceSource(1000000), args);
        }
    }

    public static SayService getSayService() {
        return sayService;
    }

}
