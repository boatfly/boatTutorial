package com.boatfly.designpattern.decorator;

public class BatterCake extends ABatterCake {
    @Override
    protected String getDesc() {
        return "煎饼";
    }

    @Override
    protected int getCost() {
        return 5;
    }
}
