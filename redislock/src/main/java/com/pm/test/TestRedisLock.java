package com.pm.test;

import com.pm.service.WorkService;
import com.pm.thread.Worker;
import com.pm.util.RedisLockUtil;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestRedisLock {

    private static JedisPool jedisPool = null;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(200);
        jedisPoolConfig.setMaxIdle(8);
        jedisPoolConfig.setMaxWaitMillis(1000*100);
        jedisPoolConfig.setTestOnBorrow(true);

        jedisPool = new JedisPool(jedisPoolConfig, "192.168.13.134", 6379, 10000, "wh14274493");
    }

    public static void main(String[] args) {
        RedisLockUtil redisLockUtil = new RedisLockUtil(jedisPool);
        WorkService workService = new WorkService(redisLockUtil);
        ExecutorService executor = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 50; i++) {
            executor.execute(new Worker(workService));
//            new Thread(new Worker(workService),i+"").start();
        }
    }
}
