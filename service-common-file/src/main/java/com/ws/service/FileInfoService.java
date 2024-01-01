package com.ws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ws.pojo.FileInfo;

import java.util.List;

public interface FileInfoService extends IService<FileInfo> {

    /**
     * 添加文件信息
     *
     * @param fileInfo 文件信息
     * @return boolean
     */
    boolean addFileInfo(FileInfo fileInfo);

    /**
     * 按id删除文件信息
     *
     * @param id id
     * @return boolean
     */
    boolean deleteFileInfoById(Long id);

    /**
     * 通过标签获取文件信息
     *
     * @param tagId 标签id
     * @return {@link List}<{@link FileInfo}>
     */
    List<FileInfo> getFileInfoByTag(Long tagId);

}
