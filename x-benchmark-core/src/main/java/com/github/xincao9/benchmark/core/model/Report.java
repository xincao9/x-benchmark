package com.github.xincao9.benchmark.core.model;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author 510655387@qq.com
 */
public class Report {

    private int concurrent; // 并发数
    private int total; // 总请求数
    private AtomicLong concurrenceCostTime = new AtomicLong(0); // 并行话费的时间和
    private long startTime; // 自然时间
    private int errorNumber; // 错误数
    private long minCostTime = Integer.MAX_VALUE; // 最小花费时间
    private long maxCostTime = Integer.MIN_VALUE; // 最大花费时间
    private int messageSize = -1;

    private static Report report;

    private Report() {
    }

    public static Report getInstance() {
        if (report == null) {
            report = new Report();
        }
        return report;
    }

    public int getConcurrent() {
        return concurrent;
    }

    public void setConcurrent(int concurrent) {
        this.concurrent = concurrent;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public AtomicLong getConcurrenceCostTime() {
        return concurrenceCostTime;
    }

    public void setConcurrenceCostTime(AtomicLong concurrenceCostTime) {
        this.concurrenceCostTime = concurrenceCostTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(int errorNumber) {
        this.errorNumber = errorNumber;
    }

    public long addConcurrenceCostTime(long n) {
        return concurrenceCostTime.addAndGet(n);
    }

    public int addErrorNumber(int n) {
        return errorNumber += n;
    }

    public long getMinCostTime() {
        return minCostTime;
    }

    public void setMinCostTime(long minCostTime) {
        this.minCostTime = minCostTime;
    }

    public void changeMinCostTime(long minCostTime) {
        if (minCostTime < this.minCostTime) {
            this.minCostTime = minCostTime;
        }
    }

    public long getMaxCostTime() {
        return maxCostTime;
    }

    public void setMaxCostTime(long maxCostTime) {
        this.maxCostTime = maxCostTime;
    }

    public void changeMaxCostTime(long maxCostTime) {
        if (maxCostTime > this.maxCostTime) {
            this.maxCostTime = maxCostTime;
        }
    }

    public int getMessageSize() {
        return messageSize;
    }

    public void setMessageSize(int messageSize) {
        this.messageSize = messageSize;
    }

}
