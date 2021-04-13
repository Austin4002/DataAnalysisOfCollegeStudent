package com.ngx.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ngx.boot.bean.StuCourse;
import com.ngx.boot.mapper.StuCourseMapper;
import com.ngx.boot.service.StuCourseService;
import org.springframework.stereotype.Service;

@Service
public class StuCourseServiceImpl extends ServiceImpl<StuCourseMapper, StuCourse> implements StuCourseService {
}
