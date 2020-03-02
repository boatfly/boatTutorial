package com.boatfly.designpattern.proxy.dynamic.jdk;

public class Client {
    public static void main(String[] args) {
        ITeather target = new Teather();

        ITeather tearch = (ITeather) new ProxyFactory(target).newProxyInstance();

        tearch.teach();
    }
}
