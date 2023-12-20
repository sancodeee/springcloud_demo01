package com.ws.service.impl;

import cn.hutool.core.io.FileUtil;
import com.ws.service.FileInfoService;
import com.ws.service.FileOperationsService;
import com.ws.vo.FileUploadVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 文件操作服务实现
 *
 * @author wangsen_a
 * @date 2023/12/19
 */
@Service
@Slf4j
public class FileOperationsServiceImpl implements FileOperationsService {

    @Autowired
    private FileInfoService fileInfoService;

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
     * 下载接口API路径
     */
    private static final String DOWNLOAD_SERVICE_API = "/download/singleFile/";

    /**
     * 文件上传
     *
     * @param multipartFile 文件
     * @param request       请求
     * @return {@link String}
     * @throws IOException ioexception
     */
    @Override
    public FileUploadVO fileUpload(HttpServletRequest request, MultipartFile multipartFile) throws IOException {
        // 原始文件名(带后缀)
        String originalFilename = multipartFile.getOriginalFilename();
        // 文件存储路径
        if (FileUtil.exist(basePath)) {
            // 如果当前存储文件的父级目录不存在则创建
            FileUtil.mkdir(basePath);
        }
        // 如果保存的文件已存在，那么就要重命名一个文件名称
        if (FileUtil.exist(basePath + File.separator + originalFilename)) {
            originalFilename = generateNewFilename(basePath, originalFilename);
        }
        File saveFile = new File(basePath + File.separator + originalFilename);
        // 存储文件 到服务器指定目录
        multipartFile.transferTo(saveFile);
        String url = "http://" + ip + ":" + port + DOWNLOAD_SERVICE_API + originalFilename;
        FileUploadVO uploadVO = new FileUploadVO();
        uploadVO.setDownloadUrl(url);
        uploadVO.setSavePath(basePath + File.separator + originalFilename);
        log.info("下载路径：{}", url);
        return uploadVO;
    }

    /**
     * 生成新文件名
     *
     * @param basePath         基本路径
     * @param originalFilename 原始文件名
     * @return {@link String}
     */
    private static String generateNewFilename(String basePath, String originalFilename) {
        File file = new File(basePath, originalFilename);
        // 文件名称
        String mainName = FileUtil.mainName(originalFilename);
        // 文件后缀名
        String extName = FileUtil.extName(originalFilename);

        int count = 1;
        while (file.exists()) {
            String numberedFilename = mainName + "(" + count + ")." + extName;
            file = new File(basePath, numberedFilename);
            count++;
        }
        return file.getName();
    }

    /**
     * 文件下载
     *
     * @param fileName 文件名称
     * @param response 响应
     * @throws IOException ioexception
     */
    @Override
    public void fileDownload(HttpServletResponse response, String fileName) throws IOException {
        String filePath = basePath + File.separator + fileName;
        if (!FileUtil.exist(filePath)) {
            // 文件不存在设置响应头状态404
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            log.error("文件不存在");
            return;
        }
        // 对文件名进行编码，指定字符集为UTF-8
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
        // 设置响应头告诉浏览器以附件形式下载文件
        response.setHeader("Content-Disposition", "attachment; filename=" + encodedFileName);
        response.setContentType("application/octet-stream");
        // 文件的字节流数组
        byte[] bytes = FileUtil.readBytes(filePath);
        // try-with-resource语句自动关闭流
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件预览
     *
     * @param response 响应
     * @param fileName 文件名称
     * @throws IOException ioexception
     */
    @Override
    public void filePreview(HttpServletResponse response, String fileName) throws IOException {
        String filePath = basePath + File.separator + fileName;
        if (!FileUtil.exist(filePath)) {
            // 文件不存在设置响应头状态404
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            log.error("文件不存在");
        }
        // 文件字节流数组
        byte[] bytes = FileUtil.readBytes(filePath);
        try (ServletOutputStream os = response.getOutputStream()) {
            os.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
