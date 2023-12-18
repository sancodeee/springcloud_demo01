package com.ws.feign;

import com.ws.common.Result;
import com.ws.pojo.TagInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "tagInfoService")
public interface TagTreeAPI {

    /**
     * 通过父级id获取一级子标签信息
     *
     * @param id id
     * @return {@link Result}<{@link List}<{@link TagInfo}>>
     */
    @GetMapping("/tagInfo/getByParent")
    Result<List<TagInfo>> getTagInfoByParent(@RequestParam(value = "id") Long id);

}
