package com.pm.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

public class RedisLockUtil {

    private JedisPool jedisPool;

    private static long waitTimeout = 10000l;

    private static int expireTime = 10;

    private static final String REDIS_KEY = "redis_lock";

    public RedisLockUtil(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public String getRedisLock() {
        Jedis conn = null;
        try {
            conn = jedisPool.getResource();
            String value = UUID.randomUUID().toString();
//            System.out.println("value: "+value);
            long now = System.currentTimeMillis();
            while (System.currentTimeMillis() < now + waitTimeout) {
                if (conn.setnx(REDIS_KEY, value) == 1) {
                    conn.expire(REDIS_KEY, expireTime);
                    return value;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn!=null){
                conn.close();
            }
        }
        return null;
    }


    public void releaseRedisLock(String value) {

        Jedis conn = null;
        //取出来的值一样才可以保证是同一把锁
        try {
            conn = jedisPool.getResource();
            if (value.equals(conn.get(REDIS_KEY))) {
                conn.del(REDIS_KEY);
                System.out.println("成功的释放锁...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn!=null){
                conn.close();
            }
        }
    }
}
