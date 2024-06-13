package com.ws.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ws.mapper.FileInfoMapper;
import com.ws.pojo.FileInfo;
import com.ws.thread.FileThreadOne;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.*;

@Slf4j
@Component
public class TestOne {

    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Test
    public void getFileAndTagInfo() throws ExecutionException, InterruptedException {
        FileThreadOne fileThreadOne = new FileThreadOne(fileInfoMapper);

        // 创建一个固定大小的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        // 提交线程到线程池去执行
        Future<Object> future1 = executorService.submit(fileThreadOne);
        Future<Object> future2 = executorService.submit(fileThreadOne);
        Future<Object> future3 = executorService.submit(fileThreadOne);

        Object o = future1.get();
        Object o1 = future2.get();
        Object o2 = future3.get();
        System.out.println(o.toString());
        System.out.println(o1.toString());
        System.out.println(o2.toString());
    }

    @Test
    public void getFile() throws ExecutionException, InterruptedException {
        FutureTask<Object> futureTask = new FutureTask<>(() -> {
            return "hello world";
        });
        Thread thread = new Thread(futureTask);
        thread.start();
        Object o = futureTask.get();
        System.out.println(o.toString());
    }

    @Test
    public void test() throws InterruptedException, ExecutionException {
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        Callable<List<FileInfo>> callable = () -> {
            LambdaQueryWrapper<FileInfo> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(FileInfo::getTagId, 2);
            List<FileInfo> fileInfos = fileInfoMapper.selectList(wrapper);
            System.out.println("查询结果：" + fileInfos);
            return fileInfos;
        };
        Future<List<FileInfo>> future = threadPool.submit(callable);
        List<FileInfo> fileInfos = future.get();
        System.out.println(fileInfos);
    }

}
