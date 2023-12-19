package com.ws.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FileOperationsService {

    /**
     * 文件上传
     *
     * @param multipartFile 文件
     * @return {@link String}
     */
    String fileUpload(HttpServletRequest request, MultipartFile multipartFile) throws IOException;

    /**
     * 文件下载
     *
     * @param response 响应
     * @param fileName 文件名称
     * @throws IOException ioexception
     */
    void fileDownload(HttpServletResponse response, String fileName) throws IOException;

}
