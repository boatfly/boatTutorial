package com.boatfly.designpattern.proxy.statics;

public class Client {
    public static void main(String[] args) {
        Teather teather = new Teather();

        TeatherProxy teatherProxy = new TeatherProxy(teather);

        teatherProxy.teach();
    }
}
