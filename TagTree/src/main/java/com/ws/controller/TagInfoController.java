package com.ws.controller;

import com.ws.pojo.TagInfo;
import com.ws.service.TagInfoService;
import com.ws.vo.TagInfoVO;
import com.ws.vo.TagInfoVO2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签信息控制器
 *
 * @author wangsen
 * @date 2023/12/09
 */
@Slf4j
@RestController
@RequestMapping(value = "/tagInfo")
public class TagInfoController {

    @Autowired
    private TagInfoService tagInfoService;


    /**
     * 添加标签信息
     *
     * @param tagInfo
     * @param result
     * @return {@link Boolean}
     */
    @PostMapping(value = "/add")
    public Boolean addTagInfo(@RequestBody TagInfo tagInfo, BindingResult result) {
        log.info("校验结果：{}", result.getAllErrors());
        return tagInfoService.addTagInfo(tagInfo);
    }


    /**
     * 根据标签id获取该标签的子标签
     *
     * @param id 主键id
     * @return {@link List}<{@link TagInfo}>
     */
    @GetMapping(value = "/getByParent")
    public List<TagInfo> getTagInfoByParent(@RequestParam(value = "id") Long id) {
        return tagInfoService.getTagInfoByParent(id);
    }


    /**
     * 根据标签id查询所有子标签
     *
     * @param id 主键id
     * @return {@link List}<{@link TagInfoVO2}>
     */
    @GetMapping(value = "/getAllChildByParent")
    public List<TagInfoVO2> getAllChildByParent(@RequestParam(value = "id") Long id) {
        return tagInfoService.getAllChildByParent(id);
    }

    /**
     * 递归方式获取该标签下所有子标签
     *
     * @param id id
     * @return {@link List}<{@link TagInfoVO2}>
     */
    @GetMapping(value = "/getAllChildByParent2")
    public List<TagInfoVO> getAllChildByParent2(@RequestParam(value = "id") Long id) {
        return tagInfoService.getAllChildByParRecursive(id);
    }


}
