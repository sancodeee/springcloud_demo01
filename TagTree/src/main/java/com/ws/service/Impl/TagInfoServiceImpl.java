package com.ws.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ws.dao.TagInfoMapper;
import com.ws.pojo.TagInfo;
import com.ws.service.TagInfoService;
import com.ws.vo.TagInfoVO2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
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

    /**
     * 根据id查询下层子标签
     *
     * @param parentId
     * @return
     */
    @Override
    public List<TagInfo> getTagInfoByParent(Long parentId) {
        if (parentId == null) {
            log.info("入参为空！");
            return Collections.EMPTY_LIST;
        }
        return tagInfoMapper.getTagInfoByParent(parentId);
    }

    /**
     * 根据id查询该标签下所有子标签
     *
     * @param parentId Long
     * @return List<TagInfoVO>
     */
    @Override
    public List<TagInfoVO2> getAllChildByParent(Long parentId) {
        // 返回所有子标签信息
        List<TagInfoVO2> tagInfoVO2s = new ArrayList<>();
        // 查出该标签下的一级子标签
        List<TagInfo> tagInfos = tagInfoMapper.getTagInfoByParent(parentId);
        // 根据一级子标签查询二级子标签
        for (TagInfo tagInfo : tagInfos) {
            TagInfoVO2 tagInfoVO2 = new TagInfoVO2();
            // 二级子标签
            List<TagInfo> tagInfosTwo = tagInfoMapper.getTagInfoByParent(tagInfo.getId());
            // 将一级子标签信息存储到VO里
            BeanUtils.copyProperties(tagInfo, tagInfoVO2);
            // 将二级子标签也存储到VO里
            tagInfoVO2.setChildNodes(tagInfosTwo);
            tagInfoVO2s.add(tagInfoVO2);
        }
        return tagInfoVO2s;
    }
}
