package com.example.web;

import com.example.services.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/token")
public class RedisAction {

    @Autowired
    private RedisService redisService;

    @RequestMapping("/set/{value}/{timeout}")
    public String setToken(@PathVariable String value,@PathVariable Long timeout){

        redisService.setValue(UUID.randomUUID()+"",value,timeout);
        return "success";
    }

    @RequestMapping("/get/{key}")
    public String getToken(@PathVariable String key){
        return redisService.getValue(key);
    }
}
