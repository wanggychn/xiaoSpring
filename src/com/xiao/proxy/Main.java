package com.xiao.proxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) throws Exception {
        Animal doo = new Dog();
        Animal instance = (Animal) Proxy.newProxyInstance(Animal.class.getClassLoader(), new Class[]{Animal.class}, new AnimalHandler(doo));
        instance.say();



        // 反射demo
//        Class<?> clazz = Class.forName("com.xiao.proxy.TargetObj");
//        TargetObj instance = (TargetObj) clazz.newInstance();
//        Method[] declaredMethods = clazz.getDeclaredMethods();
//        for (Method method : declaredMethods) {
//            System.out.println(method.getName());
//        }
//
//        Method publicMethod = clazz.getDeclaredMethod("publicMethod", String.class);
//        publicMethod.invoke(instance, "xn");
//
//        System.out.println("===");
//
//        Field val = clazz.getDeclaredField("val");
//        val.setAccessible(true);
//        val.set(instance, "HuTao");
//
//        System.out.println("===");
//
//        Method privateMethod = clazz.getDeclaredMethod("privateMethod");
//        privateMethod.setAccessible(true);
//        privateMethod.invoke(instance);

    }

}
