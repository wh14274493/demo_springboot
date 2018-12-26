package com.pm.service;

import com.pm.util.RedisLockUtil;
import org.apache.commons.lang3.StringUtils;

public class WorkService {

    private RedisLockUtil redisLockUtil;
    public WorkService(RedisLockUtil redisLockUtil){
        this.redisLockUtil = redisLockUtil;
    }

    public void doSomething(){
        String value = redisLockUtil.getRedisLock();
        if(!StringUtils.isEmpty(value)){
            System.out.println(Thread.currentThread().getName()+"获得锁，正在处理相关的业务逻辑...");
        }
        redisLockUtil.releaseRedisLock(value);
    }
}
