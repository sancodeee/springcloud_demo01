package com.ws.vo;

import com.ws.pojo.TagInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * 标签信息VO
 *
 * @author wangsen_a
 * @date 2023/12/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TagInfoVO extends TagInfo {

    /**
     * 子节点
     */
    List<TagInfoVO> childNodes;

}
