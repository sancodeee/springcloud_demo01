package com.ws.biz;

import cn.hutool.core.io.FileUtil;
import com.ws.pojo.FileInfo;
import com.ws.service.FileInfoService;
import com.ws.service.FileOperationsService;
import com.ws.vo.FileUploadVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Slf4j
public class FileProcessingBIZ {

    @Autowired
    private FileOperationsService fileOperationsService;

    @Autowired
    private FileInfoService fileInfoService;

    /**
     * 保存文件
     *
     * @param request       请求
     * @param multipartFile 多部分文件
     * @return boolean
     */
    public FileUploadVO saveFile(HttpServletRequest request, MultipartFile multipartFile, Long tagId) throws IOException {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setTagId(tagId);
        // 保存文件本体并返回下载路径
        FileUploadVO uploadVO = fileOperationsService.fileUpload(request, multipartFile);
        fileInfo.setFilePath(uploadVO.getSavePath());
        // 文件下载路径
        String url = uploadVO.getDownloadUrl();
        // 获取文件名称
        String originalFilename = url.substring(url.lastIndexOf("/") + 1);
        log.info("文件名称：{}", originalFilename);
        fileInfo.setFileName(originalFilename);
        //获取文件后缀名(文件类型)
        String extName = FileUtil.extName(originalFilename);
        fileInfo.setFileType(extName);
        // 保存文件信息
        boolean addFlag = fileInfoService.addFileInfo(fileInfo);
        uploadVO.setSaveSuccess(addFlag);
        log.info("文件信息保存结果：{}", addFlag);
        return uploadVO;
    }

}
