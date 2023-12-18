package com.ws.feign;

import com.ws.common.Result;
import com.ws.pojo.TagInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "tagInfoService")
public interface TagTreeClient {

    @GetMapping("/tagInfo/getByParent")
    public Result<List<TagInfo>> getTagInfoByParent(@RequestParam(value = "id") Long id);

}
