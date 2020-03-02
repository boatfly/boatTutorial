package com.boatfly.designpattern.proxy.dynamic.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProxyFactory implements MethodInterceptor {
    private Object target;

    public ProxyFactory(Object o) {
        this.target = o;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("Cglib is start~~~~");
        Object returnValue = method.invoke(target, args);
        System.out.println("Cglib is submit~~~~");
        return returnValue;
    }

    public Object getProxyInstance(){
        // 创建一个工具类
        Enhancer enhancer = new Enhancer();
        // 设置父类
        enhancer.setSuperclass(target.getClass());
        //设置回调函数
        enhancer.setCallback(this);
        // 创建子类对象，即代理对象
        return enhancer.create();
    }
}
