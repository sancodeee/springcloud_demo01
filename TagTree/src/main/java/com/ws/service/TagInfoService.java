package com.ws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ws.pojo.TagInfo;
import com.ws.vo.TagInfoVO;
import com.ws.vo.TagInfoVO2;

import java.util.List;

/**
 * 标签信息服务
 *
 * @author wangsen_a
 * @date 2023/12/11
 */
public interface TagInfoService extends IService<TagInfo> {

    /**
     * 添加标签信息
     *
     * @param tagInfo 标签信息
     * @return {@link Boolean}
     */
    Boolean addTagInfo(TagInfo tagInfo);

    /**
     * 通过父id获取一级子标签信息
     *
     * @param parentId 父id
     * @return {@link List}<{@link TagInfo}>
     */
    List<TagInfo> getTagInfoByParent(Long parentId);

    /**
     * 获取该标签下所有子标签
     *
     * @param parentId 父id
     * @return {@link List}<{@link TagInfoVO2}>
     */
    List<TagInfoVO2> getAllChildByParent(Long parentId);

    /**
     * 递归方式获取该标签下所有子标签信息
     *
     * @param id id
     * @return {@link List}<{@link TagInfoVO}>
     */
    List<TagInfoVO> getAllChildByParRecursive(Long id);
}
