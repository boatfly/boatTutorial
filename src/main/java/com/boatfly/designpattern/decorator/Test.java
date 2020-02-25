package com.boatfly.designpattern.decorator;

public class Test {
    public static void main(String[] args) {
        ABatterCake aBatterCake = new BatterCake();//做一个煎饼
        aBatterCake = new EggDecorator(aBatterCake);//加一个鸡蛋
        aBatterCake = new EggDecorator(aBatterCake);//再加一个鸡蛋
        aBatterCake = new SausageDecorator(aBatterCake);//加一个香肠
        System.out.println(aBatterCake.getDesc() + " 价格为：" + aBatterCake.getCost());
    }
}
