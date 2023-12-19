package com.ws.controller;

import com.ws.common.Result;
import com.ws.service.FileOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping(value = "/upload")
public class FileUploadController {

    @Autowired
    private FileOperationsService fileOperationsService;

    /**
     * 文件上传
     *
     * @param request 请求
     * @param file    文件
     * @return {@link Result}<{@link ?}>
     * @throws IOException ioexception
     */
    @PostMapping(value = "/file")
    public Result<?> fileUpload(HttpServletRequest request, @RequestBody MultipartFile file) throws IOException {
        String url = fileOperationsService.fileUpload(request, file);
        String resultData = "下载路径：" + url;
        return Result.SUCCESS("上传成功", resultData);
    }

}
