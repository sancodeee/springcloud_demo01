package com.ws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ws.pojo.TagInfo;
import com.ws.vo.TagInfoVO2;

import java.util.List;

public interface TagInfoService extends IService<TagInfo> {

    Boolean addTagInfo(TagInfo tagInfo);

    List<TagInfo> getTagInfoByParent(Long parentId);

    List<TagInfoVO2> getAllChildByParent(Long parentId);
}
