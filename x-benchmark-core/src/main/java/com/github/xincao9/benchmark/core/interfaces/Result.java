/**
 * 欢迎浏览和修改代码，有任何想法可以email我
 */
package com.github.xincao9.benchmark.core.interfaces;

/**
 *
 * @author 510655387@qq.com
 */
public interface Result {

    public void output(int concurrency, int total, int messageSize, long minRT, long maxRT, double averageRT, double tps, double errorNumber);

}
