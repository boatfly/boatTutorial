package com.boatfly.designpattern.decorator;

public class SausageDecorator extends AbstractDecorator {
    public SausageDecorator(ABatterCake aBatterCake) {
        super(aBatterCake);
    }

    @Override
    protected String getDesc() {
        return super.getDesc() + "加一个香肠";
    }

    @Override
    protected int getCost() {
        return super.getCost() + 2;
    }
}