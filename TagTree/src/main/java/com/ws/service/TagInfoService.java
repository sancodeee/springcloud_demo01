package com.ws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ws.pojo.TagInfo;
import com.ws.vo.TagInfoVO;

import java.util.List;

public interface TagInfoService extends IService<TagInfo> {

    Boolean addTagInfo(TagInfo tagInfo);

    List<TagInfo> getTagInfoByParent(Long parentId);

    List<TagInfoVO> getAllChildByParent(Long parentId);
}
