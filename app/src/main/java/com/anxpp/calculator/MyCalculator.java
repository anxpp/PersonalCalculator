package com.anxpp.calculator;

/**
 * 计算类
 * Created by anxpp on 2016/3/1.
 * @author anxpp.com
 */
public class MyCalculator {
    //固定公式一
    public static double exp1(double A,double B, double N){
        return N*B-A;
    }
    //固定公式儿
    public static double exp2(double A,double B, double N){
        return B-(A-B)/(N-1);
    }
    /**
     * 其他
     * 实现自定义表达式等
     * 自定义参数
     */
}
