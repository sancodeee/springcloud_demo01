package com.ws.vo;

import com.ws.pojo.TagInfo;
import lombok.Data;

import java.util.List;

/**
 * 返回给前端的树形结构
 */
@Data
public class TagInfoVO extends TagInfo {

    List<TagInfoVO> childNodes;

}
