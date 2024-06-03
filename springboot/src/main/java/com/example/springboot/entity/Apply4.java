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
public class Apply4 implements Serializable {

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
     * 材料
     */
    @Alias("材料")
    private String file;

    /**
     * 学生
     */
    @Alias("学生")
    private String user;

    /**
     * 审核人
     */
    @Alias("审核人")
    private String reviewer;

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

    /**
     * 毕设
     */
    @Alias("毕设")
    private Integer projectsId;

    private String notes;
    private String scores;
}
