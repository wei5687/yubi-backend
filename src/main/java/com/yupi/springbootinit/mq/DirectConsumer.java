package com.yupi.springbootinit.mq;

import com.rabbitmq.client.*;

public class DirectConsumer {
    // 定义我们正在监听的交换机名称
    private static final String EXCHANGE_NAME = "direct_exchange";

    public static void main(String[] argv) throws Exception {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置连接工厂的主机地址为本地主机
        factory.setHost("localhost");
        // 建立与 RabbitMQ 服务器的连接
        Connection connection = factory.newConnection();
        // 创建一个通道
        Channel channel = connection.createChannel();
        // 声明一个 direct 类型的交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        // 绑定xiaoyu路由键
        String queueName = "xiaoyu_queue";
        //设置持久化，非独占，非自动删除
        channel.queueDeclare(queueName, true, false, false, null);
        //队列绑定交换机
        channel.queueBind(queueName, EXCHANGE_NAME, "xiaoyu");


        // 绑定xiaopi路由键
        String queueName2 = "xiaopi_queue";
        //设置持久化，非独占，非自动删除
        channel.queueDeclare(queueName2, true, false, false, null);
        //队列绑定交换机
        channel.queueBind(queueName2, EXCHANGE_NAME, "xiaopi");

        System.out.println(" [*] Waiting for messages");

        //xiaoyu接收消息
        DeliverCallback xiaoyuDeliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [xiaoyu] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        //xiaopi接收消息
        DeliverCallback xiaopiDeliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [xiaopi] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        //xiaoyu消费消息，自动确认已消费
        channel.basicConsume(queueName, true, xiaoyuDeliverCallback, consumerTag -> {

        });

        //xiaopi消费消息，自动确认已消费
        channel.basicConsume(queueName2, true, xiaopiDeliverCallback, consumerTag -> {

        });
    }
}
