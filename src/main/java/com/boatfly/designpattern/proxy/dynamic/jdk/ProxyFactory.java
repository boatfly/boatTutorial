package com.boatfly.designpattern.proxy.dynamic.jdk;

import java.lang.reflect.Proxy;

public class ProxyFactory {
    private Object target;

    public ProxyFactory(Object o) {
        this.target = o;
    }

    public Object newProxyInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), (proxy, method, args) -> {
            Object result = method.invoke(target, args);
            return result;
        });
    }
}
