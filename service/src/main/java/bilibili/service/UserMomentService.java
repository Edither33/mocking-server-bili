package bilibili.service;

import bilibili.config.RocketMQUtil;
import bilibili.constant.RedisConstant;
import bilibili.constant.UserMomentsConstant;
import bilibili.dao.UserMomentsDao;
import bilibili.entity.UserMoments;
import bilibili.exception.ConditionException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserMomentService {

    @Resource
    private UserMomentsDao userMomentsDao;

    @Resource(name = "momentsProducer")
    private DefaultMQProducer producer;

    @Resource(name = "momentsConsumer")
    private DefaultMQPushConsumer consumer;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 用户添加动态, 并通知该用户的所有粉丝
     * @param moments 添加的动态
     */
    public void addMoments(UserMoments moments){
        moments.setCreateTime(new Date());
        userMomentsDao.insert(moments);

        // 通知
        Message message = new Message(UserMomentsConstant.TOPIC_MOMENTS,
                JSONObject.toJSONString(moments).getBytes(StandardCharsets.UTF_8));
        try {
            RocketMQUtil.syncSendMessage(producer, message);
        } catch (Exception e) {
            log.error("动态更新通知失败,", e);
            throw new ConditionException("动态更新通知失败");
        }
    }

    /**
     * 获取订阅的动态
     * @param userId 订阅人Id
     * @return 订阅的动态
     */
    public List<UserMoments> getUserSubscribedMoments(Long userId) {
        String key = RedisConstant.SUBSCRIED_KEY + userId;
        String subscribedListStr = redisTemplate.opsForValue().get(key);
        return JSONObject.parseArray(subscribedListStr, UserMoments.class);
    }
}
