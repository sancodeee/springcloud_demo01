package com.ws.service.impl;

import cn.hutool.core.io.FileUtil;
import com.ws.service.FileOperationsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Service
public class FileOperationsServiceImpl implements FileOperationsService {

    /**
     * 文件父级路径
     */
    @Value("${file.basePath}")
    private String basePath;

    @Value("${file.ip}")
    private String ip;

    @Value("${server.port}")
    private String port;

    /**
     * 文件上传
     *
     * @param multipartFile 文件
     * @param request       请求
     * @return {@link String}
     * @throws IOException ioexception
     */
    @Override
    public String fileUpload(HttpServletRequest request, MultipartFile multipartFile) throws IOException {
        // 原始文件名(带后缀)
        String originalFilename = multipartFile.getOriginalFilename();
        // 文件名称
        String mainName = FileUtil.mainName(originalFilename);
        // 文件后缀
        String extName = FileUtil.extName(originalFilename);
        // 当前目录
        String projectPath = System.getProperty("user.dir");
        // 文件存储路径
        if (FileUtil.exist(basePath)) {
            // 如果当前存储文件的父级目录不存在则创建
            FileUtil.mkdir(basePath);
        }
        // 如果保存的文件已存在，那么就要重命名一个文件名称
        if (FileUtil.exist(basePath + File.separator + originalFilename)) {
            originalFilename = System.currentTimeMillis() + "_" + mainName + "." + extName;
        }
        File saveFile = new File(basePath + File.separator + originalFilename);
        multipartFile.transferTo(saveFile);
        String url = "http://" + ip + ":" + port + "/file/download/" + originalFilename;

        return url;
    }

    /**
     * 文件下载
     *
     * @param fileName 文件名称
     * @param request  请求
     * @param response 响应
     * @throws IOException ioexception
     */
    @Override
    public void fileDownload(String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filePath = basePath + File.separator + fileName;
        if (!FileUtil.exist(filePath)) {
            return;
        }
        // 文件的字节流数组
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }
}
