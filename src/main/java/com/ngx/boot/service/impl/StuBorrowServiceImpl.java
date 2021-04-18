package com.ngx.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ngx.boot.bean.StuBorrow;
import com.ngx.boot.mapper.StuBorrowMapper;
import com.ngx.boot.service.StuBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StuBorrowServiceImpl extends ServiceImpl<StuBorrowMapper, StuBorrow> implements StuBorrowService {

    @Autowired
    private StuBorrowMapper stuBorrowMapper;


    @Override
    public List<StuBorrow> getStuNoDistinct() {
        List<StuBorrow> StuBorrowList = stuBorrowMapper.getStuNoDistinct();
        return StuBorrowList;
    }

    @Override
    public double getAvgBorrowTimeByNo(String stuNo) {
        double avg = stuBorrowMapper.getAvgBorrowTimeByNo(stuNo);
        return avg;
    }
}
