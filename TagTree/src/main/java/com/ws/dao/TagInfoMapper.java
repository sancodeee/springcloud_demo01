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

    @Select("select * from sc_tag_info where parent_id = #{parentId} order by sort_num")
    List<TagInfo> getTagInfoByParent(@Param("parentId") Long parentId);

    List<TagInfoVO> getAllChildByParent(@Param("parentId") Long parentId);

}
