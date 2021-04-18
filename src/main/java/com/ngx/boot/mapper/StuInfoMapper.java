package com.ngx.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ngx.boot.bean.StuInfo;

import java.util.List;

public interface StuInfoMapper extends BaseMapper<StuInfo> {
    List<String> getGradeNumber();

}
