package com.ws.controller;

import com.ws.common.Result;
import com.ws.feign.TagTreeAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 调用远程服务接口测试
 *
 * @author wangsen_a
 * @date 2023/12/18
 */
@RequestMapping("/consumerOne")
@RestController
public class TagTreeController {

    /**
     * feign客户端注入
     */
    @Autowired
    private TagTreeAPI tagTreeAPI;


    /**
     * 调用远程服务接口测试
     * 根据父id获取标签
     *
     * @param id id
     * @return {@link Result}<{@link ?}>
     */
    @GetMapping("/getById")
    public Result<?> getTagByParentId(@RequestParam(name = "id", required = true) Long id) {
        // 通过openFeign调用远程服务接口
        return tagTreeAPI.getTagInfoByParent(id);
    }

}
