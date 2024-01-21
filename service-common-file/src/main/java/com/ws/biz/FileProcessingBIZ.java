package com.ws.biz;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ws.dao.FileInfoMapper;
import com.ws.pojo.FileInfo;
import com.ws.service.FileInfoService;
import com.ws.service.FileOperationsService;
import com.ws.vo.FileUploadVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

/**
 * 文件处理业务
 *
 * @author wangsen
 * @date 2024/01/18
 */
@Component
@Slf4j
public class FileProcessingBIZ {

    private final FileOperationsService fileOperationsService;

    private final FileInfoService fileInfoService;

    private final FileInfoMapper fileInfoMapper;

    @Autowired
    public FileProcessingBIZ(FileOperationsService fileOperationsService, FileInfoService fileInfoService, FileInfoMapper fileInfoMapper) {
        this.fileOperationsService = fileOperationsService;
        this.fileInfoService = fileInfoService;
        this.fileInfoMapper = fileInfoMapper;
    }

    /**
     * 保存文件
     *
     * @param multipartFile 多部分文件
     * @param tagId         标签id
     * @return {@link FileUploadVO}
     * @throws IOException ioexception
     */
    public FileUploadVO saveFile(MultipartFile multipartFile, Long tagId) throws IOException {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setTagId(tagId);
        // 保存文件本体并返回下载路径
        FileUploadVO uploadVO = fileOperationsService.fileUpload(multipartFile);
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
        boolean addFlag = false;
        if (fileInfoService.getBaseMapper().insert(fileInfo) > 0) {
            uploadVO.setSaveSuccess(true);
        }
        log.info("文件信息保存结果：{}", addFlag);
        return uploadVO;
    }

    /**
     * 线程测试
     *
     * @return {@link List}<{@link FileInfo}>
     */
    public List<FileInfo> threadTest(Long tagId) throws ExecutionException, InterruptedException {
        // 单线程线程池
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        // 线程一
        Callable<List<FileInfo>> callable = () -> {
            LambdaQueryWrapper<FileInfo> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(FileInfo::getTagId, tagId);
            List<FileInfo> list = fileInfoMapper.selectList(wrapper);
            return list;
        };
        Future<List<FileInfo>> fileInfoListFuture = fixedThreadPool.submit(callable);
        // 线程二
        Callable<List<FileInfo>> callable1 = () -> {
            LambdaQueryWrapper<FileInfo> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(FileInfo::getTagId, tagId);
            List<FileInfo> list = fileInfoMapper.selectList(wrapper);
            return list;
        };
        Future<List<FileInfo>> future = fixedThreadPool.submit(callable1);
        List<FileInfo> fileInfos = fileInfoListFuture.get();
        List<FileInfo> fileInfos1 = future.get();
        return fileInfos;
    }

}
