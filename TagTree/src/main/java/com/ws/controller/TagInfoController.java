package com.ws.controller;

import com.ws.common.Result;
import com.ws.common.interceptor.RespResult;
import com.ws.pojo.TagInfo;
import com.ws.service.TagInfoService;
import com.ws.vo.TagInfoVO;
import com.ws.vo.TagInfoVO2;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 标签信息控制器
 *
 * @author wangsen
 * @date 2023/12/09
 */
@Slf4j
@RefreshScope
@Accessors(chain = true)
@RestController
@RequestMapping(value = "/tagInfo")
public class TagInfoController {

    @Autowired
    private TagInfoService tagInfoService;

    @Value("${tagInfo.name}")
    private String name;


    /**
     * 添加标签信息
     *
     * @param tagInfo 标签信息
     * @param result  结果
     * @return {@link Result}<{@link ?}>
     */
    @RespResult
    @PostMapping(value = "/add")
    public Result<?> addTagInfo(@Validated @RequestBody TagInfo tagInfo, BindingResult result) {
        log.info("nacos服务器中配置文件的值：{}", name);
        // 入参校验
        List<ObjectError> allErrors = result.getAllErrors();
        log.info("入参校验信息：{}", allErrors);
        if (!allErrors.isEmpty()) {
            // 校验信息返回
            String errMsgs = allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("；"));
            return Result.FAIL(errMsgs);
        }
        return tagInfoService.addTagInfo(tagInfo);
    }

    /**
     * 根据标签id获取该标签的子标签
     *
     * @param id id
     * @return {@link Result}<{@link List}<{@link TagInfo}>>
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
     * @param id id
     * @return {@link Result}<{@link List}<{@link TagInfoVO2}>>
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
     * @return {@link Result}<{@link List}<{@link TagInfoVO}>>
     */
    @GetMapping(value = "/getAllChildByParent2")
    public Result<List<TagInfoVO>> getAllChildByParent2(@RequestParam(value = "id") Long id) {
        List<TagInfoVO> tagInfoVOS = tagInfoService.getAllChildByParRecursive(id);
        if (tagInfoVOS.isEmpty()) {
            return Result.FAIL("查询不到数据");
        }
        return Result.SUCCESS(tagInfoVOS);
    }


    /**
     * 自定义注释测试
     *
     * @return {@link String}
     */
    @RespResult
    @GetMapping(value = "/customAnnotation")
    public List<?> customAnnotationTest(){
        Result<List<TagInfoVO>> result = this.getAllChildByParent2(Long.valueOf(1));
        List<TagInfoVO> list = result.getData();
        return list;
    }


}
