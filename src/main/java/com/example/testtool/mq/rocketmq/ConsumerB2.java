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
        consumer.setConsumeThreadMin(4);
        consumer.setConsumeThreadMax(4);
        consumer.setPullBatchSize(3);
        consumer.setConsumeMessageBatchMaxSize(1);
        consumer.setPullInterval(10000);
        int min = 1;
        int max = 10;

        Random random = new Random();



        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                System.out.println("msgssss：　" + msgs.size());
//                int value = random.nextInt(max + min) + min;
//                System.out.println(value);
//                try {
//                    Thread.sleep(10*1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        //Launch the consumer instance.
        consumer.start();

        System.out.printf("Consumer Started.%n");
    }
}
