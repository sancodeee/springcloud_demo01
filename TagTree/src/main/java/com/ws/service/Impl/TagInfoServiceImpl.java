package com.ws.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ws.dao.TagInfoMapper;
import com.ws.pojo.TagInfo;
import com.ws.service.TagInfoService;
import com.ws.vo.TagInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class TagInfoServiceImpl extends ServiceImpl<TagInfoMapper, TagInfo> implements TagInfoService {

    @Autowired
    private TagInfoMapper tagInfoMapper;

    @Override
    public Boolean addTagInfo(TagInfo tagInfo) {
        if (!ObjectUtils.isEmpty(tagInfo)) {
            if (this.save(tagInfo)) {
                log.info("插入成功");
                return true;
            }
            return false;
        }
        log.info("入参为空");
        return false;
    }

    @Override
    public List<TagInfo> getTagInfoByParent(Long parentId) {
        if (parentId == null) {
            log.info("入参为空！");
            return Collections.EMPTY_LIST;
        }
        return tagInfoMapper.getTagInfoByParent(parentId);
    }

    /**
     * 根据父节点id查询到所有子节点
     *
     * @param parentId Long
     * @return List<TagInfoVO>
     */
    @Override
    public List<TagInfoVO> getAllChildByParent(Long parentId) {

        return null;
    }
}
