package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import cn.hutool.core.annotation.Alias;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 
 */
@Getter
@Setter
public class Projects implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Alias("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 毕设题目
     */
    @Alias("毕设题目")
    private String name;

    /**
     * 介绍
     */
    @Alias("介绍")
    private String content;

    /**
     * 附件
     */
    @Alias("附件")
    private String file;

    /**
     * 老师
     */
    @Alias("老师")
    private String teacher;

    /**
     * 审核状态
     */
    @Alias("审核状态")
    private String state;

    /**
     * 审核描述
     */
    @Alias("审核描述")
    private String reason;


}
