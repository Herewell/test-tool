/**
 * Author:   Herewe
 * Date:     2022/4/4 1:02
 * Description:
 */
package com.example.testtool.mq.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

public class Producer {
    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer("producerGroup1");
        // Specify name server addresses.
        producer.setNamesrvAddr("114.132.232.241:9876");
        //Launch the instance.
        producer.start();
        producer.setDefaultTopicQueueNums(5);
        for (int i = 0; i < 200; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("topicTest1" /* Topic */,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ " +
                            i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
//            producer.send(msg, (MessageQueueSelector) (mqs, msg1, arg) -> null,i % 5);
            SendResult sendResult = producer.send(msg,10000);
            System.out.printf("%s%n", sendResult);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();

    }
}
