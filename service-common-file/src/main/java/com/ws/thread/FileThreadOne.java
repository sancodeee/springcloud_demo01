package com.ws.thread;

import com.ws.mapper.FileInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.Callable;

@Component
public class FileThreadOne implements Callable<Object> {

    private final FileInfoMapper fileInfoMapper;

    @Autowired
    public FileThreadOne(FileInfoMapper fileInfoMapper) {
        this.fileInfoMapper = fileInfoMapper;
    }

    @Override
    public HashMap<String, String> call() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "zhangsan");
        map.put("age", "17");
        map.put("address", "东大街");
        return map;
    }
}
