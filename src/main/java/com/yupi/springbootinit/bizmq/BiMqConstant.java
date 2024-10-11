package com.yupi.springbootinit.bizmq;

public interface BiMqConstant {
    //业务交换机
    String BI_EXCHANGE_NAME = "bi_exchange";
    //业务队列
    String BI_QUEUE_NAME = "bi_queue";
    //业务路由key
    String BI_ROUTING_KEY = "bi_routingKey";
    //ai 模型id
    long BI_MODEL_ID = 1659171950288818178L;
    //死信交换机
    String dead_EXCHANGE_NAME = "dead_exchange";
    //死信队列
    String dead_QUEUE_NAME = "dead_queue";
    //死信路由key
    String DEAD_ROUTING_KEY = "dead_routingKey";
}
