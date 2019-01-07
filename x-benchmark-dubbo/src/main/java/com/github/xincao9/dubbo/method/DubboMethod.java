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
package com.github.xincao9.dubbo.method;

import com.github.xincao9.benchmark.core.annotation.Test;
import com.github.xincao9.benchmark.core.interfaces.Method;
import com.github.xincao9.dubbo.XBenchmarkDubbo;
import java.util.List;

/**
 *
 * @author xincao9@gmail.com
 */
@Test(name = "dubbo")
public class DubboMethod extends Method {

    @Override
    public void exec(Object params) throws Exception {
        List<String> list = (List<String>) params;
        String service = list.get(0);
        String version = list.get(1);
        String method = list.get(2);
        String parameterTypes = list.get(3);
        String parameters = list.get(4);
        XBenchmarkDubbo.getGenericService().$invoke(XBenchmarkDubbo.getMethod(), null, null);
    }

}
