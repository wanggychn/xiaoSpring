package com.xiao.proxy;

public class TargetObj {
    private String val;

    public TargetObj() {
        val = "xiao";
    }

    public void publicMethod(String str) {
        System.out.println("this is " + str);
    }

    private void privateMethod() {
        System.out.println("private method " + val);
    }


}
