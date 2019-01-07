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
package com.github.xincao9.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.github.xincao9.benchmark.core.XBenchmarkCore;
import com.github.xincao9.benchmark.core.util.FileSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author xincao9@gmail.com
 */
@SpringBootApplication
public class XBenchmarkDubbo {

    @Value("mode")
    private static boolean mode = true;
    @Value("name")
    private static String name;
    @Value("registry.address")
    private static String registryAddress;
    @Value("registry.username")
    private static String registryUsername;
    @Value("registry.password")
    private static String registryPassword;
    @Value("service")
    private static String service;
    @Value("method")
    private static String method;
    @Value("version")
    private static String version;
    private static GenericService genericService;

    public static void main(String... args) {
        mode = Boolean.valueOf(System.getProperty("mode", "true"));
        if (mode) {
            int[] indexs = {0, 1};
            XBenchmarkCore.bootstrap(new FileSource("", "||", indexs), args);
        } else {
            SpringApplication.run(XBenchmarkDubbo.class, args);
        }
        ApplicationConfig application = new ApplicationConfig();
        application.setName(name);
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress(registryAddress);
        registry.setUsername(registryUsername);
        registry.setPassword(registryPassword);
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setInterface(service);
        reference.setVersion(version);
        reference.setGeneric(true);
        reference.setApplication(application);
        reference.setRegistry(registry);
        genericService = reference.get();
    }

    public static GenericService getGenericService() {
        return genericService;
    }

    public static String getService () {
        return service;
    }

    public static String getMethod() {
        return method;
    }
}
