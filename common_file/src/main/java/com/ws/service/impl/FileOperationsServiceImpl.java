package com.ws.service.impl;

import com.ws.service.FileOperationsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileOperationsServiceImpl implements FileOperationsService {

    @Value("${file.basePath}")
    private String basePath;


    /**
     * 文件上传
     *
     * @param multipartFile 文件
     * @return {@link String}
     */
    @Override
    public String fileUpload(HttpServletRequest request, MultipartFile multipartFile) throws IOException {
        // 原始文件名
        String originalFilename = multipartFile.getOriginalFilename();
        // 获取文件后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 随机生成uuid组合后缀组成新的文件名
        String fileName = UUID.randomUUID().toString() + suffix;
        // 创建目录
        File dir = new File(basePath);
        if (!dir.exists()) {
            // 如果目录不存在则创建
            dir.mkdir();
        }
        multipartFile.transferTo(new File(basePath + fileName));
        return "success";
    }

    /**
     * 文件下载
     *
     * @param fileName 文件名称
     * @param request  请求
     * @param response 响应
     * @return {@link String}
     */
    @Override
    public String fileDownload(String fileName, HttpServletRequest request, HttpServletResponse response) {

        return null;
    }
}
