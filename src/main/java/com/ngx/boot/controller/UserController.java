package com.ngx.boot.controller;

import com.ngx.boot.bean.StuInfo;
import com.ngx.boot.service.StuBorrowService;
import com.ngx.boot.service.StuInfoService;
import com.ngx.boot.vo.Result;
import com.ngx.boot.vo.portrait.Portrait;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private StuInfoService stuInfoService;

    @Autowired
    private StuBorrowService stuBorrowService;


    @GetMapping("/portrait")
    public Result getPortrait(@RequestParam(value = "stuId" ,required = true) String stuId) throws InvocationTargetException, IllegalAccessException {

        Result rs = new Result<>(500, "error");

        Portrait portrait = new Portrait();
        StuInfo stuInfo = stuInfoService.getById(stuId);
        Map<String, Object> map = new HashMap<>();
        map.put("stu_name","zhangsan");
        map.put("stu_no","2");
        stuInfoService.listByMap(map);


        BeanUtils.copyProperties(stuInfo,portrait);
        portrait.setStuId(stuId);
        portrait.setStuGender(stuInfo.getStuSex());



        return rs;
    }

    public Result getScore(  @RequestParam String stuId){

        Result rs=new Result();


        return rs;

    }



}
