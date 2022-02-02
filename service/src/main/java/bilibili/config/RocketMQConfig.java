package bilibili.config;

import bilibili.constant.RedisConstant;
import bilibili.constant.UserMomentsConstant;
import bilibili.entity.UserFollowing;
import bilibili.entity.UserMoments;
import bilibili.service.UserFollowingService;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;


@Configuration
@Slf4j
public class RocketMQConfig {
    @Value("${rocket.nameserver.addr}")
    private String nameServerAddr;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserFollowingService userFollowingService;

    @Bean("momentsProducer")
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(UserMomentsConstant.MOMENTS_GROUP);
        producer.setNamesrvAddr(nameServerAddr);
        producer.start();
        return producer;
    }

    @Bean("momentsConsumer")
    public DefaultMQPushConsumer defaultMQPushConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(UserMomentsConstant.MOMENTS_GROUP);
        consumer.setNamesrvAddr(nameServerAddr);
        consumer.subscribe(UserMomentsConstant.TOPIC_MOMENTS, "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt messageExt : list) {
                    log.info(messageExt.toString());
                }
                MessageExt messageExt = list.get(0);
                String mse = new String(messageExt.getBody());
                UserMoments userMoments = JSONObject.toJavaObject(JSONObject.parseObject(mse), UserMoments.class);
                Long userId = userMoments.getUserId();
                // 查找这个人的粉丝
                List<UserFollowing> userFans = userFollowingService.getUserFans(userId);
                // 向粉丝发送动态更新的消息
                for (UserFollowing userFan : userFans) {
                    String key = RedisConstant.SUBSCRIED_KEY + userFan.getUserId();
                    String subscribedListStr = redisTemplate.opsForValue().get(key);
                    List<UserMoments> subscribedList;
                    if(StrUtil.isBlankIfStr(subscribedListStr)) {
                        subscribedList = new ArrayList<>();
                    }else {
                        subscribedList = JSONObject.parseArray(subscribedListStr, UserMoments.class);
                    }
                    subscribedList.add(userMoments);
                    redisTemplate.opsForValue().set(key, JSONObject.toJSONString(subscribedList));
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        return consumer;
    }
}
