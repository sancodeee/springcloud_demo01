package com.ws.controller;

import com.ws.common.Result;
import com.ws.pojo.TagInfo;
import com.ws.service.TagInfoService;
import com.ws.vo.TagInfoVO;
import com.ws.vo.TagInfoVO2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
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
    public Result<?> addTagInfo(@Validated @RequestBody TagInfo tagInfo, BindingResult result) {
        List<ObjectError> allErrors = result.getAllErrors();
        log.info("校验信息：{}", allErrors);
        if (!allErrors.isEmpty()) {
            return Result.FAIL(allErrors.get(0).getDefaultMessage());
        }
        if (!tagInfoService.verifyAddTag(tagInfo)) {
            return Result.FAIL("同级不能添加同名标签");
        }
        if (!tagInfoService.addTagInfo(tagInfo)) {
            return Result.FAIL();
        }
        return Result.SUCCESS();
    }


    /**
     * 根据标签id获取该标签的子标签
     *
     * @param id 主键id
     * @return {@link List}<{@link TagInfo}>
     */
    @GetMapping(value = "/getByParent")
    public Result<List<TagInfo>> getTagInfoByParent(@RequestParam(value = "id") Long id) {
        List<TagInfo> tagInfos = tagInfoService.getTagInfoByParent(id);
        if (tagInfos.isEmpty()) {
            return Result.FAIL("查询不到数据");
        }
        return Result.SUCCESS(tagInfos);
    }


    /**
     * 根据标签id查询所有子标签
     *
     * @param id 主键id
     * @return {@link List}<{@link TagInfoVO2}>
     */
    @GetMapping(value = "/getAllChildByParent")
    public Result<List<TagInfoVO2>> getAllChildByParent(@RequestParam(value = "id") Long id) {
        List<TagInfoVO2> allChildList = tagInfoService.getAllChildByParent(id);
        if (allChildList.isEmpty()) {
            return Result.FAIL("查询不到数据信息");
        }
        return Result.SUCCESS(allChildList);
    }

    /**
     * 递归方式获取该标签下所有子标签
     *
     * @param id id
     * @return {@link List}<{@link TagInfoVO2}>
     */
    @GetMapping(value = "/getAllChildByParent2")
    public Result<List<TagInfoVO>> getAllChildByParent2(@RequestParam(value = "id") Long id) {
        List<TagInfoVO> tagInfoVOS = tagInfoService.getAllChildByParRecursive(id);
        if (tagInfoVOS.isEmpty()) {
            return Result.FAIL("查询不到数据");
        }
        return Result.SUCCESS(tagInfoVOS);
    }


}
