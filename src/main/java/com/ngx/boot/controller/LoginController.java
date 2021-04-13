package com.ngx.boot.controller;

import com.ngx.boot.bean.StuInfo;
import com.ngx.boot.vo.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    @PostMapping("/user/login")
    public Result login(@RequestBody StuInfo student){
        Result rs = new Result<>(500, "error");



        return rs;

    }


    @GetMapping("/currentUser")
    public Result getCurrentUser(){
        Result rs = new Result<>(500, "error");



        return rs;

    }




}
