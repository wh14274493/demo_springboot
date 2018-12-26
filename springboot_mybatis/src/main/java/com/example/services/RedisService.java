package com.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 调用jdk的序列化将对象存储到硬盘上
     * 需要实体类实现Serializable接口
     */
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *
     * @param hashKey 健
     * @param obj 值
     * @param timeout 超时时间（可为null）
     * @return
     */
    public boolean setValue(String hashKey, Object obj, Long timeout) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            String value = (String) obj;
            redisTemplate.opsForValue().set(hashKey, value);
            if (timeout != null) {
                redisTemplate.expire(hashKey,timeout, TimeUnit.SECONDS);
            }
            return true;
        }else if(obj instanceof List){

        }
        return false;
    }

    public String getValue(String hashKey){
        if(hashKey==null||"".equals(hashKey)){
            return "FAILED TO GET VALUE";
        }
        String value = (String)redisTemplate.opsForValue().get(hashKey);
        return value;
    }

}
