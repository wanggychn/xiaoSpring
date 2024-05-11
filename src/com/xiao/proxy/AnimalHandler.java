package com.xiao.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AnimalHandler implements InvocationHandler {

    private final Object object;

    public AnimalHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before say");
        Object invoke = method.invoke(object, args);
        System.out.println("after say");
        return invoke;
    }

}
