package bilibili.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RocketMQUtil {

    public static void syncSendMessage(DefaultMQProducer producer, Message message) throws Exception {
        SendResult send = producer.send(message);
        log.info("发送消息", send);
    }

    public static void asyncSendMessage(DefaultMQProducer producer, Message message) throws Exception {
        SendResult send = producer.send(message);
        CountDownLatch countDownLatch = new CountDownLatch(2);
        for(int i = 0; i < 2; i++) {
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    countDownLatch.countDown();
                    log.info("发送消息成功", send);
                }

                @Override
                public void onException(Throwable throwable) {
                    countDownLatch.countDown();
                    log.info("发送消息失败", send, throwable.getCause());
                }
            });
        }
        countDownLatch.await(5, TimeUnit.SECONDS);
    }
}
