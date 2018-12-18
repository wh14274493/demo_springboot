package com.example.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext = applicationContext;
    }

    //获取ApplicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    //通过name获取bean
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }
    //通过class对象获取bean
    public static <T> T getBean(Class<T> c) {
        return applicationContext.getBean(c);
    }
}
