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
public class Clazz implements Serializable {

    private static final long serialVersionUID = 1L;

    @Alias("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Alias("班级名称")
    private String name;

    @Alias("班级介绍")
    private String content;


}
