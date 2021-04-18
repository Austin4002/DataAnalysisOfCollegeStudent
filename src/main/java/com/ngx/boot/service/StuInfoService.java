package com.ngx.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ngx.boot.bean.StuInfo;

import java.util.List;

public interface StuInfoService extends IService<StuInfo> {
    List<String> getGradeNumber();

}
