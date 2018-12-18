package com.pm.demo;

import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JedisMain {

    public static void main(String[] args) {

        Jedis jedis = new Jedis("192.168.43.169", 6379);

        System.out.println(jedis.ping());
        System.out.println(jedis.get("mystr"));


        Map<String, String> map = new HashMap<>();
        map.put("item", "java");
        map.put("item1", "c++");
        hsPut(jedis,"mapKey",map);
        hsGetAllEntries(jedis,"mapKey");
    }

    //存放map类型
    public static void hsPut(Jedis jedis, String hashKey, Map<String, String> map) {
        jedis.hmset(hashKey, map);
    }

    //取map类型
    public static void hsGetAllEntries(Jedis jedis, String hashKey) {
        System.out.println(jedis.hgetAll(hashKey).toString());
    }
}
