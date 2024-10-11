package com.yupi.springbootinit.bizmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class BiInitMain {
    // 定义交换机的名称为"code_exchange"
    public static final String NORMAL_EXCHANGE = BiMqConstant.BI_EXCHANGE_NAME;
    public static final String DEAD_EXCHANGE = BiMqConstant.dead_EXCHANGE_NAME;

    // 创建队列，随机分配一个队列名称
    public static final String NORMAL_QUEUE = BiMqConstant.BI_QUEUE_NAME;
    public static final String DEAD_QUEUE = BiMqConstant.dead_QUEUE_NAME;

    public static final String BI_ROUTING_KEY = BiMqConstant.BI_ROUTING_KEY;
    public static final String DEAD_ROUTING_KEY = BiMqConstant.DEAD_ROUTING_KEY;

    public static void main(String[] args) {
        try {
            //业务生产者初始化
            // 创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");

            // 创建连接
            Connection connection = factory.newConnection();

            // 创建通道
            Channel channel = connection.createChannel();

            // 声明交换机，指定交换机类型为 direct
            channel.exchangeDeclare(NORMAL_EXCHANGE, "direct");
            channel.exchangeDeclare(DEAD_EXCHANGE, "direct");

            //业务队列绑定死信交换机
            Map<String, Object> maps = new HashMap<>();
            maps.put("x-dead-letter-exchange", DEAD_EXCHANGE);
            maps.put("x-dead-letter-routing-key", DEAD_ROUTING_KEY);

            // 声明队列，设置队列持久化、非独占、非自动删除，并传入额外的参数为 null
            channel.queueDeclare(NORMAL_QUEUE, false, false, false, maps);
            channel.queueDeclare(DEAD_QUEUE, false, false, false, null);
            //绑定队列
            channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, BI_ROUTING_KEY);
            channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, DEAD_ROUTING_KEY);
        } catch (Exception e) {
            // 异常处理
            e.printStackTrace();
        }
    }
}
