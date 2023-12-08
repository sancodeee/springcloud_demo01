package com.ws.controller;

import com.ws.pojo.TagInfo;
import com.ws.service.TagInfoService;
import com.ws.vo.TagInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/tagInfo")
public class TagInfoController {

    @Autowired
    private TagInfoService tagInfoService;

    /**
     * 添加标签信息
     *
     * @param tagInfo TagInfo
     * @param result  BindingResult
     * @return Boolean
     */
    @PostMapping(value = "/add")
    public Boolean addTagInfo(@RequestBody TagInfo tagInfo, BindingResult result) {
        log.info("校验结果：{}", result.getAllErrors());
        return tagInfoService.addTagInfo(tagInfo);
    }

    /**
     * 根据标签id获取该标签的子标签
     *
     * @param id Long
     * @return List<TagInfo>
     */
    @GetMapping(value = "/getByParent")
    public List<TagInfo> getTagInfoByParent(@RequestParam(value = "id") Long id) {
        return tagInfoService.getTagInfoByParent(id);
    }

    @GetMapping(value = "/getAllChildByParent")
    public List<TagInfoVO> getAllChildByParent(@RequestParam(value = "id") Long id) {
        return null;
    }


}
