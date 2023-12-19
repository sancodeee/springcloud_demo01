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

    void fileDownload(HttpServletResponse response, String fileName) throws IOException;

}
