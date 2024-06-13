package com.ws.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ws.entity.pojo.TagInfo;
import com.ws.entity.vo.TagInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 标签信息映射器
 *
 * @author wangsen_a
 * @date 2023/12/12
 */
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
