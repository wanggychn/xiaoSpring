package com.xiao.service;

import com.xiao.spring.XiaoApplicationContext;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException {
        XiaoApplicationContext applicationContext = new XiaoApplicationContext(AppConfig.class);
        System.out.println(applicationContext.getBean("userService"));
        System.out.println(applicationContext.getBean("userService"));
        System.out.println(applicationContext.getBean("userService"));
        System.out.println(applicationContext.getBean("userService"));
    }
}
