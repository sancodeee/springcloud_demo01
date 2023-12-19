package com.ws.controller;

import com.ws.service.FileOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
     * @return {@link String}
     */
    @PostMapping(value = "/file")
    public String fileUpload(HttpServletRequest request, MultipartFile file) throws IOException {
        return fileOperationsService.fileUpload(request, file);
    }

}
