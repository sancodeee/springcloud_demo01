package com.ws.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ws.pojo.TagInfo;
import com.ws.vo.TagInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TagInfoMapper extends BaseMapper<TagInfo> {

    /**
     * 通过父级获取标签信息
     *
     * @param parentId 父id
     * @return {@link List}<{@link TagInfo}>
     */
    @Select("select * from sc_tag_info where parent_id = #{parentId} order by sort_num")
    List<TagInfo> getTagInfoByParent(@Param("parentId") Long parentId);


    /**
     * 查询该标签下所有子标签
     *
     * @param id id
     * @return {@link List}<{@link TagInfoVO}>
     */
    List<TagInfoVO> getAllChildByParent2(@Param("id") Long id);

}
