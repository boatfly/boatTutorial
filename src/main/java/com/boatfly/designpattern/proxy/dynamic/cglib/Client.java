package com.boatfly.designpattern.proxy.dynamic.cglib;


public class Client {
    public static void main(String[] args) {
        Teather target = new Teather();

        Teather teacher = (Teather) new ProxyFactory(target).getProxyInstance();

        teacher.teach("boat");
    }
}
