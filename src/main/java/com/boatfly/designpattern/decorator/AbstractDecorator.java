package com.boatfly.designpattern.decorator;

public class AbstractDecorator extends ABatterCake {
    private ABatterCake aBatterCake;

    public AbstractDecorator(ABatterCake aBatterCake) {
        this.aBatterCake = aBatterCake;
    }

    @Override
    protected String getDesc() {
        return this.aBatterCake.getDesc();
    }

    @Override
    protected int getCost() {
        return this.aBatterCake.getCost();
    }
}
