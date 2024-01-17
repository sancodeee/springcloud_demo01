package com.ws.controller;

import com.ws.biz.FileProcessingBIZ;
import com.ws.common.Result;
import com.ws.service.FileOperationsService;
import com.ws.vo.FileUploadVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping(value = "/upload")
public class FileUploadController {

    @Autowired
    private FileOperationsService fileOperationsService;

    @Autowired
    private FileProcessingBIZ fileProcessingBIZ;

    /**
     * 文件上传
     *
     * @param request 请求
     * @param file    文件
     * @return {@link Result}<{@link ?}>
     * @throws IOException ioexception
     */
    @PostMapping(value = "/singleFile")
    public Result<?> fileUpload(HttpServletRequest request, @RequestBody MultipartFile file) throws IOException {
        FileUploadVO uploadVO = fileOperationsService.fileUpload(file);
        return Result.SUCCESS("上传成功", uploadVO);
    }

    /**
     * 文件上传并保存信息
     *
     * @param request 请求
     * @param file    文件
     * @param tagId   标签id
     * @return {@link Result}<{@link ?}>
     * @throws IOException ioexception
     */
    @PostMapping(value = "/singleFile/addInfo")
    public Result<?> fileUploadAndSaveInfo(HttpServletRequest request, @RequestBody MultipartFile file,
                                           @RequestParam("tagId") Long tagId) throws IOException {
        FileUploadVO uploadVO = fileProcessingBIZ.saveFile(request, file, tagId);
        if (!uploadVO.getSaveSuccess()) {
            return Result.FAIL("文件信息保存失败");
        }

        return Result.SUCCESS("文件上传并保存成功", uploadVO);
    }

}
