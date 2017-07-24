package com.kevinyin.lnetty.baseio.Oio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 封装用于处理socket
 * Created by kevinyin on 2017/7/24.
 */
public class TimeServerHandlerExecutePool {
    private ExecutorService executor ;

    public TimeServerHandlerExecutePool(int maxPoolSize,int queueSize) {
        this.executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                maxPoolSize,120L, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void execute(Runnable task){
        executor.execute(task);
    }
}
