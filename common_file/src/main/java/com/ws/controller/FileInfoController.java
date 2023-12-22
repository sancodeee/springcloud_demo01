package com.ws.controller;

import com.ws.biz.FileProcessingBIZ;
import com.ws.common.Result;
import com.ws.pojo.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/thread")
public class FileInfoController {

    @Autowired
    private FileProcessingBIZ fileProcessingBIZ;

    @GetMapping("/get/all")
    public Result<?> getFileInfoByTagId(Long tagId) throws ExecutionException, InterruptedException {
        List<FileInfo> fileInfos = fileProcessingBIZ.threadTest(tagId);
        return Result.SUCCESS(fileInfos);
    }

}
