package com.ws.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * tagInfo实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sc_tag_info")
public class TagInfo {

    /**
     * 主键id
     */
    @NotNull
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 父节点id
     */
    private Long parentId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String updater;

    /**
     * 逻辑删除：0-未删除，1-已删除
     */
    private Integer isDeleted;

    /**
     * 排序字段
     */
    private Integer sortNum;


}
