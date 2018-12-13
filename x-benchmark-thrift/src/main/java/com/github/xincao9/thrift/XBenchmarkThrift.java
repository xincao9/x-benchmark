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
package com.github.xincao9.thrift;

import com.github.xincao9.benchmark.core.XBenchmarkCore;
import com.github.xincao9.benchmark.core.util.SequenceSource;
import com.github.xincao9.thrift.api.service.PingService;
import com.github.xincao9.thrift.server.PingServiceImpl;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author xincao9@gmail.com
 */
@SpringBootApplication
public class XBenchmarkThrift {

    private static final Logger LOGGER = LoggerFactory.getLogger(XBenchmarkThrift.class);

    public static ThreadLocal<PingService.Client> CLIENTS = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            try {
                TTransport transport = new TSocket("localhost", 6666);
                TProtocol protocol = new TBinaryProtocol(transport);
                transport.open();
                return new PingService.Client(protocol);
            } catch (Throwable e) {
                LOGGER.error(e.getMessage());
            }
            return null;
        }

    };

    private static boolean mode;

    public static void main(String... args) throws Throwable {
        mode = Boolean.valueOf(System.getProperty("mode", "true"));
        if (mode) {
            TServerTransport serverTransport = new TServerSocket(6666);
            TThreadPoolServer.Args ttpsArgs = new TThreadPoolServer.Args(serverTransport);
            ttpsArgs.processor(new PingService.Processor(new PingServiceImpl()));
            ttpsArgs.protocolFactory(new TBinaryProtocol.Factory());
            TServer server = new TThreadPoolServer(ttpsArgs);
            server.serve();
        } else {
            XBenchmarkCore.bootstrap(new SequenceSource(1000000), args);
        }
    }
}
