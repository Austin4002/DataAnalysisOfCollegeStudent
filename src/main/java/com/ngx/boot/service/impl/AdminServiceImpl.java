package com.ngx.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ngx.boot.bean.Admin;
import com.ngx.boot.mapper.AdminMapper;
import com.ngx.boot.service.AdminService;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    
}
