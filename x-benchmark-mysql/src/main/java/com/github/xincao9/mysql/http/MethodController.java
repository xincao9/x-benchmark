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
package com.github.xincao9.mysql.http;

import com.github.xincao9.mysql.XDAO;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author xincao9@gmail.com
 */
@RestController
public class MethodController {

    private final AtomicLong insertCounter = new AtomicLong(0);
    private final AtomicLong selectCounter = new AtomicLong(0);

    @RequestMapping("/insert")
    public Integer insert() throws Exception {
        String value = RandomStringUtils.randomNumeric(128);
        return XDAO.add(insertCounter.incrementAndGet(), value);
    }

    @RequestMapping("/select")
    public String select() throws Exception {
        return XDAO.get(selectCounter.incrementAndGet());
    }
}
