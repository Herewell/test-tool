/**
 * Author:   Herewe
 * Date:     2022/4/4 1:02
 * Description:
 */
package com.example.testtool.mq.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;
import java.util.Random;

public class ConsumerB2 {
    public static void main(String[] args) throws InterruptedException, MQClientException {

        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumerGroup1");

        // Specify name server addresses.
        consumer.setNamesrvAddr("114.132.232.241:9876");

        // Subscribe one more more topics to consume.
        consumer.subscribe("topicTest1", "*");
        consumer.setConsumeThreadMin(2);
        consumer.setConsumeThreadMax(2);
        /**
         * 分配给这个consumer的每个queue都会拉取这个数量
         * 举个例子：这个topicTest1一共有5个读写队列。启动了两个consumer，那么consumer1分配2个，consumer2分配3个（有算法）
         * 那么consumer2每次拉10*3个，两个消费线程，每个消费5个。consumer1每次拉去10*2个，两个消费线程，每次消费5个
         * 消费者按照一定的频率拉取消息，当超过一定的阈值的消息未被消费，那么就会触发流控，不再从broker中拉取新的消息（org.apache.rocketmq.client.impl.consumer.DefaultMQPushConsumerImpl#pullMessage(org.apache.rocketmq.client.impl.consumer.PullRequest)）
         */
        consumer.setPullBatchSize(10);
        /**
         * 以5个为一批，有一个失败，则全部失败
         */
        consumer.setConsumeMessageBatchMaxSize(5);
        /**
         * 每次向broker拉取消息的间隔时间
         */
        consumer.setPullInterval(60000);
        int min = 1;
        int max = 10;

        Random random = new Random();



        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {

                System.out.println(Thread.currentThread().getName() + "开始消费 " + msgs.size());
                msgs.forEach(msg -> {
                    System.out.println(Thread.currentThread().getName() +  "consumer " + new String(msg.getBody()));
                });
                System.out.println(Thread.currentThread().getName() + "结束消费 " + msgs.size());
//                int value = random.nextInt(max + min) + min;
//                System.out.println(value);
//                try {
//                    Thread.sleep(10*1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        //Launch the consumer instance.
        consumer.start();

        System.out.printf("Consumer Started.%n");
    }
}
