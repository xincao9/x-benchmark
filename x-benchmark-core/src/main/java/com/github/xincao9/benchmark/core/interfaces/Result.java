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
/**
 * 欢迎浏览和修改代码，有任何想法可以email我
 */
package com.github.xincao9.benchmark.core.interfaces;

/**
 *
 * @author xincao9@gmail.com
 */
public interface Result {

    public void output(int concurrency, int total, int messageSize, long minRT, long maxRT, double averageRT, double tps, double errorNumber);

}
