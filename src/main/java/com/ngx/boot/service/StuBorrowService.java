package com.ngx.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ngx.boot.bean.StuBorrow;

import java.util.List;

public interface StuBorrowService extends IService<StuBorrow> {

    List<StuBorrow> getStuNoDistinct();


    double getAvgBorrowTimeByNo(String stuNo);
}
