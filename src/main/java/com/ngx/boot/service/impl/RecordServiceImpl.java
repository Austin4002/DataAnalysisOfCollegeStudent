package com.ngx.boot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ngx.boot.bean.StuBorrow;
import com.ngx.boot.mapper.RecordMapper;
import com.ngx.boot.mapper.StuBorrowMapper;
import com.ngx.boot.mapper.StuInfoMapper;
import com.ngx.boot.service.RecordService;
import com.ngx.boot.service.StuBorrowService;
import com.ngx.boot.vo.Records.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {


    @Autowired
    private RecordService recordService;

}
