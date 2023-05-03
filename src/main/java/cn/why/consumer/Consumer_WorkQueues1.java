package cn.why.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 */
public class Consumer_WorkQueues1 {
    public static void main(String[] args) throws IOException, TimeoutException {

        // 1、创建链接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 2、 设置参数
        factory.setHost("192.168.6.110");//ip  默认值 localhost
        factory.setPort(5672);//端口  默认值 5672
        factory.setVirtualHost("/admin");//虚拟机 默认值/
        factory.setUsername("admin");//用户名 默认值 guest
        factory.setPassword("admin");//密码 默认值 guest
        // 3、创建（获取）链接 Connection
        Connection connection = factory.newConnection();
        // 4、创建Channel
        Channel channel = connection.createChannel();
        // 5、创建队列QUeue
        channel.queueDeclare("work_queues",true,false,false,null);
        // 接收消息
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("new String(body) = " + new String(body));
            }
        };
        channel.basicConsume("work_queues",true,consumer);

        // 消费者不关闭资源，保持监听
        // 再次执行生产者发送消息，消费者输出第二次监听到的内容。
    }
}
