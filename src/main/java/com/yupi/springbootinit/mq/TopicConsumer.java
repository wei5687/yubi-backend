package com.yupi.springbootinit.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class TopicConsumer {

    private static final String EXCHANGE_NAME = "topic-exchange";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //主题交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        //前端队列
        String queueName = "frontend_queue";
        //持久化，非排他，非自动删除
        channel.queueDeclare(queueName, true, false, false, null);
        //绑定到交换机
        channel.queueBind(queueName, EXCHANGE_NAME, "#.前端.#");

        //后端队列
        String queueName2 = "backend_queue";
        //持久化，非排他，非自动删除
        channel.queueDeclare(queueName2, true, false, false, null);
        //绑定到交换机
        channel.queueBind(queueName2, EXCHANGE_NAME, "#.后端.#");

        //产品队列
        String queueName3 = "product_queue";
        //持久化，非排他，非自动删除
        channel.queueDeclare(queueName3, true, false, false, null);
        //绑定到交换机
        channel.queueBind(queueName3, EXCHANGE_NAME, "#.产品.#");
        //打印等待信息
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        //消息回调函数
        DeliverCallback xiaoaDeliverCallback   = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [xiaoa] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        //消息回调函数
        DeliverCallback xiaobDeliverCallback   = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [xiaob] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        //消息回调函数
        DeliverCallback xiaocDeliverCallback  = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [xiaoc] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        //绑定消息处理的回调函数到队列
        channel.basicConsume(queueName, true, xiaoaDeliverCallback, consumerTag -> {
        });

        channel.basicConsume(queueName2, true, xiaobDeliverCallback, consumerTag -> {
        });

        channel.basicConsume(queueName3, true, xiaocDeliverCallback, consumerTag -> {
        });
    }
}
