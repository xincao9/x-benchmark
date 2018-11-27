package com.github.xincao9.benchmark.core.interfaces;

import com.github.xincao9.benchmark.core.model.Report;
import com.github.xincao9.benchmark.core.util.Logger;
import java.util.ArrayList;

/**
 *
 * @author 510655387@qq.com
 */
public abstract class Method {

    abstract public void exec(Object params) throws Exception;

    public final void run(Object params) {
        long startTime = System.currentTimeMillis();
        try {
            exec(params);
        } catch (Exception ex) {
            Report.getInstance().addErrorNumber(1);
            Logger.info(String.format("exception : %s", ex.getMessage()));
        } finally {
            long costTime = System.currentTimeMillis() - startTime;
            if (costTime > 1000) {
                if (params instanceof ArrayList) {
                    Logger.info(String.format("warn params = %s time = %s", ((ArrayList<String>) params).toString(), costTime));
                } else {
                    Logger.info(String.format("warn params = %s time = %s", params.toString(), costTime));
                }
            }
            Report.getInstance().changeMaxCostTime(costTime);
            Report.getInstance().changeMinCostTime(costTime);
            Report.getInstance().addConcurrenceCostTime(costTime);
        }
    }
}
