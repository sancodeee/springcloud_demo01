package com.ws.service.impl;

import com.ws.service.FileOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class FileOperationsImpl implements FileOperations {


    /**
     * 文件上传
     *
     * @param multipartFile 文件
     * @return {@link String}
     */
    @Override
    public String fileUpload(HttpServletRequest request, MultipartFile multipartFile) {

        return null;
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
