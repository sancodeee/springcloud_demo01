package com.ws.controller;

import com.ws.common.Result;
import com.ws.service.FileOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping(value = "/download")
@RestController
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
        fileOperationsService.fileDownload(response, fileName);
        return Result.SUCCESS("下载成功");
    }

}
