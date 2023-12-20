package com.ws.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ws.dao.FileInfoMapper;
import com.ws.pojo.FileInfo;
import com.ws.service.FileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService {

    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Override
    public boolean addFileInfo(FileInfo fileInfo) {
        return this.save(fileInfo);
    }

    @Override
    public boolean deleteFileInfoById(Long id) {
        return this.removeById(id);
    }

    @Override
    public List<FileInfo> getFileInfoByTag(Long tagId) {
        if (ObjectUtil.isNull(tagId)) {
            log.error("入参为null！");
            return Collections.EMPTY_LIST;
        }
        return fileInfoMapper.selectList(new LambdaQueryWrapper<FileInfo>().eq(FileInfo::getTagId, tagId));
    }
}
