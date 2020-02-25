package com.boatfly.java8.lambda;

import org.junit.Test;

public class TestLambda3 {
    @Test
    public void test1(){
        Long aLong = valueHandler(100l, 100l, (x, y) -> x + y);
        System.out.println(aLong);
    }

    public Long valueHandler(Long l1,Long l2,MyFunction<Long,Long> mf){
        return mf.getValue(l1,l2);
    }
}
