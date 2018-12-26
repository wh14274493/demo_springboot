package com.pm.thread;

import com.pm.service.WorkService;
import com.pm.util.RedisLockUtil;

public class Worker implements Runnable{

    private WorkService workService;
    public Worker(WorkService workService){
        this.workService = workService;
    }

    @Override
    public void run() {
        workService.doSomething();
    }
}
