package com.ws.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 标签信息实体
 *
 * @author wangsen_a
 * @date 2023/12/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sc_tag_info")
@EqualsAndHashCode(callSuper = true)
public class TagInfo extends BasePO implements Serializable {

    private static final long serialVersionUID = -2013374768892598740L;
    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 标签名称
     */
    @NotBlank(message = "标签名称tagName不能为空")
    private String tagName;

    /**
     * 父节点id
     */
    @NotNull(message = "父标签parentId不能为空")
    private Long parentId;

    /**
     * 排序编号
     */
    private Integer sortNum;

}
