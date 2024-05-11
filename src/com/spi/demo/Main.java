package com.spi.demo;

import wan.spi.LoggerService;

/**
 * 通过spi机制，写出的简单日志框架
 */
public class Main {

    public static void main(String[] args) {
        LoggerService loggerService = LoggerService.getService();
        loggerService.info("spi info msg");
        System.out.println("Hello world!");
    }

}
