package com.ws.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ws.dao.TagInfoMapper;
import com.ws.pojo.TagInfo;
import com.ws.service.TagInfoService;
import com.ws.vo.TagInfoVO;
import com.ws.vo.TagInfoVO2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 标签信息服务实现
 *
 * @author wangsen_a
 * @date 2023/12/11
 */
@Slf4j
@Service
public class TagInfoServiceImpl extends ServiceImpl<TagInfoMapper, TagInfo> implements TagInfoService {

    @Autowired
    private TagInfoMapper tagInfoMapper;


    /**
     * 添加标签信息
     *
     * @param tagInfo 标签信息
     * @return {@link Boolean}
     */
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
     * 验证添加标签
     *
     * @param tagInfo 标签信息实体
     * @return {@link Boolean}
     */
    @Override
    public Boolean verifyAddTag(TagInfo tagInfo) {
        // 校验规则：同级不能有同名的标签
        LambdaQueryWrapper<TagInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TagInfo::getParentId, tagInfo.getParentId());
        List<TagInfo> tagInfos = tagInfoMapper.selectList(wrapper);
        boolean b = tagInfos.stream().anyMatch(otherTag -> tagInfo.getTagName().equals(otherTag.getTagName()));
        // 如果同级存在同名标签，则校验不通过
        if (b) {
            return false;
        }
        return true;
    }


    /**
     * 按父级id获取子标签信息
     *
     * @param parentId 父主键id
     * @return {@link List}<{@link TagInfo}>
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
     * 该方法非递归的方式，只查了两个层级
     *
     * @param id id
     * @return {@link List}<{@link TagInfoVO2}>
     */
    @Override
    public List<TagInfoVO2> getAllChildByParent(Long id) {
        // 返回所有子标签信息
        List<TagInfoVO2> tagInfoVO2s = new ArrayList<>();
        // 查出该标签下的一级子标签
        List<TagInfo> tagInfos = tagInfoMapper.getTagInfoByParent(id);
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


    /**
     * 逐段递归获取所有子标签
     *
     * @param id 主键id
     * @return {@link List}<{@link TagInfoVO}>
     */
    public List<TagInfoVO> getAllChildByParRecursive(Long id) {
        // 递归查询所有标签
        List<TagInfoVO> allChildNodes = tagInfoMapper.getAllChildByParent2(id);
        // 排除父节点后，将信息存入map方便后续使用
        // 指定了如果存入相同的key时如何处理冲突
        Map<Long, TagInfoVO> mapTemp = allChildNodes.stream().filter(node -> !id.equals(node.getId()))
                .collect(Collectors.toMap(TagInfo::getId, tagInfoVO -> tagInfoVO, (key1, key2) -> key2));
        List<TagInfoVO> tagVoList = new ArrayList<>();
        // 遍历每个元素，排除根节点
        allChildNodes.stream().filter(node -> !id.equals(node.getId())).forEach(tagInfoVO -> {
            // 如果某条数据的pid是传入的id，则放到返回的集合中
            // 如果该数据的pid不是传入的id，那就找到其父节点，并将这个数据add到其父节点的List<TreeVo> childrenTreeNodes;
            if (tagInfoVO.getParentId().equals(id)) {
                tagVoList.add(tagInfoVO);
            }
            // 找到当前节点的父节点
            TagInfoVO tagInfoVoPar = mapTemp.get(tagInfoVO.getParentId());
            if (tagInfoVoPar != null) {
                if (tagInfoVoPar.getChildNodes() == null) {
                    tagInfoVoPar.setChildNodes(new ArrayList<TagInfoVO>());
                }
                tagInfoVoPar.getChildNodes().add(tagInfoVO);
            }
        });
        return tagVoList;
    }
}
