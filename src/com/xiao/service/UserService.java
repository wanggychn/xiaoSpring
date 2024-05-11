package com.xiao.service;

import com.xiao.spring.Autowired;
import com.xiao.spring.Component;
import com.xiao.spring.Scope;

@Component(value = "userService")
@Scope(value = "singleton")
public class UserService {

    @Autowired
    private OrderService orderService;

    public void test() {
        System.out.println(orderService);
    }

}
