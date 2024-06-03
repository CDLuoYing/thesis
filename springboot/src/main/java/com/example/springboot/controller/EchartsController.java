package com.example.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Projects;
import com.example.springboot.entity.User;
import com.example.springboot.service.IProjectsService;
import com.example.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IProjectsService projectsService;


    @GetMapping("/data")
    public Result data() {
        Map<String, Object> map = new HashMap<>();
        map.put("name","未选课题数");
        map.put("value", projectsService.count(new LambdaQueryWrapper<Projects>().eq(Projects::getState,"未选")));

        Map<String, Object> map1 = new HashMap<>();
        map1.put("name","已选课题数");
        map1.put("value", projectsService.count(new LambdaQueryWrapper<Projects>().eq(Projects::getState,"已选")));

        List<Map<String, Object>> result = new ArrayList<>();
        result.add(map);
        result.add(map1);
        return Result.success(result);
    }

}
