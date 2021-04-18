package com.ngx.boot.controller;

import com.ngx.boot.vo.LoginUser;
import com.ngx.boot.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api")
@Slf4j
public class LoginController {

    @PostMapping("/login/account")
    public Result login(@RequestBody LoginUser student){
        Result rs = new Result<>(200, "ok");
        log.error("---------------------------------------->"+student.toString());


        return rs;

    }


    @GetMapping("/currentUser")
    public Result getCurrentUser(){
        Result rs = new Result<>(500, "error");



        return rs;

    }




}
