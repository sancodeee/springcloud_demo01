package com.ws.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FileOperations {

    /**
     * 文件上传
     *
     * @param multipartFile 文件
     * @return {@link String}
     */
    String fileUpload(HttpServletRequest request, MultipartFile multipartFile);

    String fileDownload(String fileName, HttpServletRequest request, HttpServletResponse response);

}
