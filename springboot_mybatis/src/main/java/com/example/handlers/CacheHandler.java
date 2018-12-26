package com.example.handlers;

import com.alibaba.fastjson.JSONObject;
import com.example.domain.User;
import com.example.services.RedisService;
import com.example.utils.EhCacheUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
public class CacheHandler {

    @Autowired
    private RedisService redisService;

    @Autowired
    private EhCacheUtils ehCacheUtils;

    @Pointcut("execution(* com.example.web..*.*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        //类名+方法名+参数
        StringBuffer key = new StringBuffer(signature.getDeclaringTypeName() + "." + signature.getName());
        String cacheName = "userCache";
        for (Object o : args) {
            key.append(o);
        }
        System.out.println("key: " + key.toString() + ",cacheName: " + cacheName);
        Object ehCach = ehCacheUtils.get(cacheName, key.toString());
        if (null != ehCach) {
            System.out.println("从ehcache中成功获取数据");
            return ehCach;
        }
        String redisCacheStr = redisService.getValue(key.toString());
        if (!StringUtils.isEmpty(redisCacheStr)) {
            Object redisCache = JSONObject.parseObject(redisCacheStr, User.class);
            ehCacheUtils.put(cacheName, key.toString(), redisCache);
            System.out.println("从redis中成功获取数据");
            return redisCache;
        }

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            System.out.println("方法执行异常，请稍后再试！");
        }

        ehCacheUtils.put(cacheName, key.toString(), result);
        redisService.setValue(key.toString(), JSONObject.toJSONString(result), null);
        System.out.println("从mysql中成功获取数据");
        return result;
    }

}
