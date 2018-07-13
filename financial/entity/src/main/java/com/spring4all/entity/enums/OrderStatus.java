package com.spring4all.entity.enums;

/**
 * @program: financial
 * @description: 订单状态
 * @author: qiankeqin
 * @create: 2018-07-14 00:42
 **/
public enum OrderStatus {
    INIT("初始化"),
    PROCESS("处理中"),
    SUCCESS("成功"),
    FAIL("失败");

    private String desc;

    OrderStatus(String desc) {
        this.desc = desc;
    }
}
