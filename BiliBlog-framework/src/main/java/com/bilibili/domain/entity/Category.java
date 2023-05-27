package com.bilibili.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 分类表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sg_category")
public class Category {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 分类名
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 父分类id，如果没有父分类为-1
     */
    @TableField(value = "pid")
    private Long pid;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 状态0:正常,1禁用
     */
    @TableField(value = "`status`")
    private String status;

    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private Long createBy;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_by",fill = FieldFill.UPDATE)
    private Long updateBy;

    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    @TableField(value = "del_flag")
    private Integer delFlag;
}