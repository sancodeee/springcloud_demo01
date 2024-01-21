package com.ws.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ws.common.ResultCodeEnum;
import com.ws.dao.FileInfoMapper;
import com.ws.exception.CustomException;
import com.ws.pojo.FileInfo;
import com.ws.service.FileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件信息服务实现
 *
 * @author wangsen
 * @date 2024/01/21
 */
@Slf4j
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService {

    private final FileInfoMapper fileInfoMapper;

    @Autowired
    public FileInfoServiceImpl(FileInfoMapper fileInfoMapper) {
        this.fileInfoMapper = fileInfoMapper;
    }

    /**
     * 通过标签获取文件信息
     *
     * @param tagId 标签id
     * @return {@link List}<{@link FileInfo}>
     */
    @Override
    public List<FileInfo> getFileInfoByTag(Long tagId) {
        if (ObjectUtil.isNull(tagId)) {
            log.error("入参为null！");
            throw new CustomException(ResultCodeEnum.PARAM_LOST_ERROR.code, ResultCodeEnum.PARAM_LOST_ERROR.msg);
        }
        return fileInfoMapper.selectList(new LambdaQueryWrapper<FileInfo>().eq(FileInfo::getTagId, tagId));
    }
}
