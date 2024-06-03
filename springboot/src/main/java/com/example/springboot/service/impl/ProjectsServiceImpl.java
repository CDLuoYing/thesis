package com.example.springboot.service.impl;

import com.example.springboot.entity.Projects;
import com.example.springboot.mapper.ProjectsMapper;
import com.example.springboot.service.IProjectsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 
 */
@Service
public class ProjectsServiceImpl extends ServiceImpl<ProjectsMapper, Projects> implements IProjectsService {

}
