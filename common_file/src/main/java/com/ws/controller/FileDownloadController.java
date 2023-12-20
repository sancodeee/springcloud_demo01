package com.ws.controller;

import com.ws.common.HttpStatusCode;
import com.ws.common.Result;
import com.ws.service.FileOperationsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(value = "/download")
public class FileDownloadController {

    @Autowired
    private FileOperationsService fileOperationsService;

    /**
     * 文件下载
     *
     * @param response 响应
     * @param fileName 文件名称
     * @return {@link Result}<{@link ?}>
     * @throws IOException ioexception
     */
    @GetMapping(value = "/singleFile/{fileName}")
    public Result<?> fileDownload(HttpServletResponse response, @PathVariable(value = "fileName") String fileName) throws IOException {
        // 下载文件
        fileOperationsService.fileDownload(response, fileName);
        // 如果状态码为404则说明要下载的文件不存在
        if (response.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
            return Result.FAIL(HttpStatusCode.FAIL_404.getCode(), HttpStatusCode.FAIL_404.getCnMessage());
        }
        log.info("下载成功");
        return null;
    }

    /**
     * 文件预览
     *
     * @param response 响应
     * @param fileName 文件名称
     * @return {@link Result}<{@link ?}>
     * @throws IOException ioexception
     */
    @GetMapping(value = "/preview/{fileName}")
    public Result<?> filePreview(HttpServletResponse response, @PathVariable(value = "fileName") String fileName) throws IOException {
        fileOperationsService.filePreview(response, fileName);
        if (response.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
            return Result.FAIL(HttpStatusCode.FAIL_404.getCode(), HttpStatusCode.FAIL_404.getCnMessage());
        }
        return null;
    }

}
