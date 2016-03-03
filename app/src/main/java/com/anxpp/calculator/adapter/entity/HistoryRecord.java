package com.anxpp.calculator.adapter.entity;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

/**
 * 历史记录实体
 * 会持久化到数据库
 * Created by anxpp on 2016/3/3.
 * @author anxpp.com
 */
@Table
public class HistoryRecord extends SugarRecord {

    private long id;
    private double A,B,N,X,Y;
    private long time;
    private String remark;

    public HistoryRecord(){}

    public HistoryRecord(double a, double b, double n, double x, double y, long time, String remark) {
        A = a;
        B = b;
        N = n;
        X = x;
        Y = y;
        this.time = time;
        this.remark = remark;
    }

    public double getA() {
        return A;
    }
    public void setA(double a) {
        A = a;
    }
    public double getB() {
        return B;
    }
    public void setB(double b) {
        B = b;
    }
    public double getN() {
        return N;
    }
    public void setN(double n) {
        N = n;
    }
    public double getX() {
        return X;
    }
    public void setX(double x) {
        X = x;
    }
    public double getY() {
        return Y;
    }
    public void setY(double y) {
        Y = y;
    }
    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
