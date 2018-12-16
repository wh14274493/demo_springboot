package com.example.web;

import com.example.domain.User;
import com.example.services.UserService;
import com.example.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserAction {

    @Autowired
    private UserService userService;

    @RequestMapping("/queryAll")
    public List<User> queryAllUser(){
        return userService.findAll();
    }

    @RequestMapping("/queryById")
    public List<User> queryById(@RequestParam("id") Long id){

        System.out.println("获取到id： "+id);
        return userService.findById(id);
    }

    @RequestMapping("/deleteById/{id}")
    public boolean deleteById(@PathVariable Long id){

        return userService.deleteById(id);
    }

    @RequestMapping("/add/{id}")
    public boolean add(@PathVariable Long id){
        User u = SpringUtil.getBean(User.class);
        System.out.println(u.toString());
        u.setId(id);
        u.setName("wanghao");
        u.setUserName("WANGHAO");
        u.setPassword("123456");
        return userService.add(u);
    }

}
